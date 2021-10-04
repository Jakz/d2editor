package com.github.jakz.d2editor.ui;

import java.util.function.Consumer;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.github.jakz.d2editor.data.CharacterClass;
import com.github.jakz.d2editor.data.DataTables;
import com.github.jakz.d2editor.data.EquipSlot;
import com.github.jakz.d2editor.data.ItemProperty;
import com.github.jakz.d2editor.data.ItemType;
import com.github.jakz.d2editor.data.MagicSuffix;
import com.pixbits.lib.ui.table.ColumnSpec;
import com.pixbits.lib.ui.table.TableModel;

public class DataViewer extends JPanel
{
  private final JTabbedPane tabs;
  
  private DataTable<MagicSuffix> magicPrefixTable;
  private DataTable<MagicSuffix> magicSuffixTable;
  private DataTable<ItemProperty> propertiesTable;
  private DataTable<ItemType> itemTypesTable;
  
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
      
      m.addColumn(new ColumnSpec<MagicSuffix, CharacterClass>("classspecific", CharacterClass.class, d -> d.clazzSpecific));
      m.addColumn(new ColumnSpec<MagicSuffix, CharacterClass>("class", CharacterClass.class, d -> d.clazz));
      m.addColumn(new ColumnSpec<MagicSuffix, Integer>("classlevelreq", Integer.class, d -> d.clazzLevelReq));
      
      m.addColumn(new ColumnSpec<MagicSuffix, Integer>("frequency", Integer.class, d -> d.frequency));
      m.addColumn(new ColumnSpec<MagicSuffix, Integer>("group", Integer.class, d -> d.group));
    };
    
    itemTypesTable = new DataTable<>(m -> {
      m.addColumn(new ColumnSpec<ItemType, String>("name", String.class, d -> d.name));
      m.addColumn(new ColumnSpec<ItemType, String>("code", String.class, d -> d.code));
      m.addColumn(new ColumnSpec<ItemType, String>("equiv1", String.class, d -> d.equiv1));
      m.addColumn(new ColumnSpec<ItemType, String>("equiv2", String.class, d -> d.equiv2));

      m.addColumn(new ColumnSpec<ItemType, EquipSlot>("bodyLoc1", EquipSlot.class, d -> d.bodyLoc1));
      m.addColumn(new ColumnSpec<ItemType, EquipSlot>("bodyLoc2", EquipSlot.class, d -> d.bodyLoc2));

    });
    
    magicPrefixTable = new DataTable<>(builder);
    magicSuffixTable = new DataTable<>(builder);
    
    propertiesTable = new DataTable<>(m -> {
      m.addColumn(new ColumnSpec<ItemProperty, String>("code", String.class, d -> d.code));
      m.addColumn(new ColumnSpec<ItemProperty, Boolean>("enabled", Boolean.class, d -> d.enabled));
    });
    
    tabs.add("ItemTypes", itemTypesTable);
    tabs.add("MagixPrefix", magicPrefixTable);
    tabs.add("MagicSuffix", magicSuffixTable);
    tabs.add("Properties", propertiesTable);

    this.add(tabs);
  }
  
  public void setData(DataTables data)
  {
    itemTypesTable.refresh(data.itemTypes.values());
    magicPrefixTable.refresh(data.magicPrefixes);
    magicSuffixTable.refresh(data.magicSuffixes);
    propertiesTable.refresh(data.properties.values());
  }
}
