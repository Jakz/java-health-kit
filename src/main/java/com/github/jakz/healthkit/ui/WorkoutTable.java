package com.github.jakz.healthkit.ui;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import com.github.jakz.healthkit.data.Sample;
import com.github.jakz.healthkit.data.SampleSet;
import com.github.jakz.healthkit.data.Value;
import com.github.jakz.healthkit.data.Workout;
import com.pixbits.lib.ui.table.ColumnSpec;
import com.pixbits.lib.ui.table.DataSource;
import com.pixbits.lib.ui.table.TableModel;
import com.pixbits.lib.ui.table.renderers.LambdaLabelTableRenderer;

public class WorkoutTable extends JTable
{
  TableModel<Workout> model;
  DataSource<Workout> data;
  DateTimeFormatter dateFormatter;
  
  public WorkoutTable(DataSource<Workout> data)
  {
    dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - kk:mm:ss Z");
    
    this.data = data;
    model = new TableModel<>(this, data);
    
    ColumnSpec<Workout, ?> typeColumn = new ColumnSpec<>("Type", String.class, s -> s.type().description());
    ColumnSpec<Workout, ?> startDate = new ColumnSpec<>("Start", ZonedDateTime.class, s -> s.timestamp().start());
    ColumnSpec<Workout, ?> endDate = new ColumnSpec<>("End", ZonedDateTime.class, s -> s.timestamp().end());
    ColumnSpec<Workout, ?> duration = new ColumnSpec<>("Duration", Value.class, s -> s.duration());
    ColumnSpec<Workout, ?> distance = new ColumnSpec<>("Distance", Value.class, s -> s.distance());
    ColumnSpec<Workout, ?> energy = new ColumnSpec<>("Energy Burned", Value.class, s -> s.energyBurned());

    startDate.setRenderer(new LambdaLabelTableRenderer<ZonedDateTime>((s, l) -> l.setText(Formatters.startDate.format(s))));
    endDate.setRenderer(new LambdaLabelTableRenderer<ZonedDateTime>((s, l) -> l.setText(Formatters.endDate.format(s))));
    
    TableCellRenderer valueRenderer = 
        new LambdaLabelTableRenderer<Value>((s, l) -> { if (s != null) l.setText(s.toString()); });
    
    duration.setRenderer(valueRenderer);
    distance.setRenderer(valueRenderer);
    energy.setRenderer(valueRenderer);
    
    model.addColumn(typeColumn);
    model.addColumn(startDate);
    model.addColumn(endDate);
    model.addColumn(duration);
    model.addColumn(distance);
    model.addColumn(energy);
    
    this.setAutoCreateRowSorter(true);
  }
}
