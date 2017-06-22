package com.github.jakz.healthkit.ui;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JTable;

import com.github.jakz.healthkit.data.Sample;
import com.github.jakz.healthkit.data.SampleSet;
import com.pixbits.lib.ui.table.ColumnSpec;
import com.pixbits.lib.ui.table.TableModel;
import com.pixbits.lib.ui.table.renderers.LambdaLabelTableRenderer;

public class SampleTable extends JTable
{
  TableModel<Sample> model;
  SampleSet data;
  DateTimeFormatter dateFormatter;
  
  public SampleTable(SampleSet data)
  {
    dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - hh:mm:ss Z");
    
    this.data = data;
    model = new TableModel<>(this, data);
    
    ColumnSpec<Sample, ?> typeColumn = new ColumnSpec<>("Type", String.class, s -> s.type().description);
    ColumnSpec<Sample, ?> startDate = new ColumnSpec<>("Start", ZonedDateTime.class, s -> s.start());
    ColumnSpec<Sample, ?> endDate = new ColumnSpec<>("End", ZonedDateTime.class, s -> s.end());
        
    startDate.setRenderer(new LambdaLabelTableRenderer<ZonedDateTime>((s, l) -> l.setText(dateFormatter.format(s))));
    endDate.setRenderer(new LambdaLabelTableRenderer<ZonedDateTime>((s, l) -> l.setText(dateFormatter.format(s))));

    
    model.addColumn(typeColumn);
    model.addColumn(startDate);
    model.addColumn(endDate);
    
    this.setAutoCreateRowSorter(true);
  }
}
