package com.github.jakz.d2editor.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Collection;
import java.util.function.Consumer;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.pixbits.lib.ui.table.DataSource;
import com.pixbits.lib.ui.table.FilterableDataSource;
import com.pixbits.lib.ui.table.TableModel;

public class DataTable<T> extends JPanel
{
  private FilterableDataSource<T> data;

  private TableModel<T> model;
  private JTable table;
  
  public DataTable(Consumer<TableModel<T>> columnBuilder)
  {
    table = new JTable();
    table.setAutoCreateRowSorter(true);
    model = new Model(table);
    
    setLayout(new BorderLayout());
    JScrollPane pane = new JScrollPane(table);
    pane.setPreferredSize(new Dimension(1024, 768));
    add(pane, BorderLayout.CENTER);

    columnBuilder.accept(model);
  }

  public void refresh(Collection<T> data)
  {
    this.data = FilterableDataSource.of(data);
    model.setData(this.data);
    model.fireTableDataChanged();
  }

  private class Model extends TableModel<T>
  {
    Model(JTable table)
    {
      super(table, DataSource.empty());
    }
  }
}
