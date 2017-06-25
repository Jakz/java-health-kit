package com.github.jakz.healthkit.ui;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JTable;

import com.github.jakz.healthkit.data.Sample;
import com.github.jakz.healthkit.data.SampleSet;
import com.github.jakz.healthkit.data.Unit;
import com.github.jakz.healthkit.data.Value;
import com.github.jakz.healthkit.data.constants.StandardUnit;
import com.pixbits.lib.ui.table.ColumnSpec;
import com.pixbits.lib.ui.table.DataSource;
import com.pixbits.lib.ui.table.TableModel;
import com.pixbits.lib.ui.table.renderers.LambdaLabelTableRenderer;

public class SampleTable extends JTable
{
  TableModel<Sample> model;
  DataSource<Sample> data;
  DateTimeFormatter dateFormatter;
  
  public SampleTable(DataSource<Sample> data)
  {
    dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - kk:mm:ss Z");
    
    this.data = data;
    model = new TableModel<>(this, data);
    
    ColumnSpec<Sample, ?> typeColumn = new ColumnSpec<>("Type", String.class, s -> s.type().description);
    ColumnSpec<Sample, ?> startDate = new ColumnSpec<>("Start", ZonedDateTime.class, s -> s.timestamp().start());
    ColumnSpec<Sample, ?> endDate = new ColumnSpec<>("End", ZonedDateTime.class, s -> s.timestamp().end());
    ColumnSpec<Sample, ?> value = new ColumnSpec<>("Value", Value.class, s -> s.value());

    startDate.setRenderer(new LambdaLabelTableRenderer<ZonedDateTime>((s, l) -> l.setText(dateFormatter.format(s))));
    endDate.setRenderer(new LambdaLabelTableRenderer<ZonedDateTime>((s, l) -> l.setText(dateFormatter.format(s))));
    value.setRenderer(new LambdaLabelTableRenderer<Value>((s, l) -> { 
      if (s != null)
      {
        if (s.unit().measureType() == Unit.Type.LENGTH)
          l.setText(s.convert(StandardUnit.CM).toString());
        else
          l.setText(s.toString());
      }
    } ));
    
    model.addColumn(typeColumn);
    model.addColumn(startDate);
    model.addColumn(endDate);
    model.addColumn(value);
    
    this.setAutoCreateRowSorter(true);
  }
}
