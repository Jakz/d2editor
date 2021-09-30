package com.github.jakz.d2editor.ui;

import com.pixbits.lib.ui.UIUtils;
import com.pixbits.lib.ui.WrapperFrame;

public class UI
{
  public final static int s = 1;
  
  public final static WrapperFrame<InventoryPanel> frame;
  
  public final static InventoryPanel inventoryPanel;
  
  static
  {
    inventoryPanel = new InventoryPanel();
    
    frame = UIUtils.buildFrame(inventoryPanel, "Inventory");
  }
  
  public static void init()
  {
    UIUtils.setNimbusLNF();
    
    frame.centerOnScreen();
    frame.exitOnClose();
    frame.setVisible(true);
  }
  
}
