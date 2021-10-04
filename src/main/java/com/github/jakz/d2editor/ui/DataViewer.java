package com.github.jakz.d2editor.ui;

import java.util.function.Consumer;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.github.jakz.d2editor.data.DataTables;
import com.github.jakz.d2editor.data.MagicSuffix;
import com.pixbits.lib.ui.table.ColumnSpec;
import com.pixbits.lib.ui.table.TableModel;

public class DataViewer extends JPanel
{
  private final JTabbedPane tabs;
  
  private DataTable<MagicSuffix> magicPrefixTable;
  private DataTable<MagicSuffix> magicSuffixTable;
  
  public DataViewer()
  {
    tabs = new JTabbedPane(JTabbedPane.LEFT);
    
    Consumer<TableModel<MagicSuffix>> builder = m -> {
      m.addColumn(new ColumnSpec<MagicSuffix, String>("name", String.class, d -> d.name));
      m.addColumn(new ColumnSpec<MagicSuffix, Boolean>("expansion", Boolean.class, d -> d.isExpansion()));
      m.addColumn(new ColumnSpec<MagicSuffix, Boolean>("spawnable", Boolean.class, d -> d.spawnable));
      m.addColumn(new ColumnSpec<MagicSuffix, Boolean>("rare", Boolean.class, d -> d.rare));
      
      m.addColumn(new ColumnSpec<MagicSuffix, Integer>("level", Integer.class, d -> d.level));
      m.addColumn(new ColumnSpec<MagicSuffix, Integer>("maxlevel", Integer.class, d -> d.levelReq));
      m.addColumn(new ColumnSpec<MagicSuffix, Integer>("levelreq", Integer.class, d -> d.levelReq));
    };
    
    magicPrefixTable = new DataTable<>(builder);
    magicSuffixTable = new DataTable<>(builder);
    
    tabs.add("MagixPrefix", magicPrefixTable);
    tabs.add("MagicSuffix", magicSuffixTable);
    
    this.add(tabs);
  }
  
  public void setData(DataTables data)
  {
    magicPrefixTable.refresh(data.magicPrefixes);
    magicSuffixTable.refresh(data.magicSuffixes);
  }
}
