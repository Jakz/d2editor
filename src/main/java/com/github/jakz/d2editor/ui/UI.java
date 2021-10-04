package com.github.jakz.d2editor.ui;

import com.pixbits.lib.ui.UIUtils;
import com.pixbits.lib.ui.WrapperFrame;

public class UI
{
  public final static int s = 1;
  
  public final static WrapperFrame<InventoryPanel> frame;
  public final static WrapperFrame<DataViewer> dataViewer;
  
  public final static InventoryPanel inventoryPanel;
  public final static DataViewer dataViewerPanel;
  
  static
  {
    inventoryPanel = new InventoryPanel();
    
    frame = UIUtils.buildFrame(inventoryPanel, "Inventory");
    
    dataViewerPanel = new DataViewer();
    dataViewer = UIUtils.buildFrame(dataViewerPanel, "Game Data");
  }
  
  public static void init()
  {
    UIUtils.setNimbusLNF();
    
    /*frame.centerOnScreen();
    frame.exitOnClose();
    frame.setVisible(true);*/
    
    dataViewer.centerOnScreen();
    dataViewer.exitOnClose();
    dataViewer.setVisible(true);
  }
  
}
