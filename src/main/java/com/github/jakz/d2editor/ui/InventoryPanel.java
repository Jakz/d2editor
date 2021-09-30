package com.github.jakz.d2editor.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.github.jakz.d2editor.App;

public class InventoryPanel extends JPanel
{
  
  
  JButton helm;

  int bx = 0, by = 0;

  
  InventoryPanel()
  {
    
    setPreferredSize(new Dimension(581, 783));
    setLayout(null);
    
    helm = new JButton("Helm");
    helm.setOpaque(false);
    add(helm);

    relayout();
  }
  
  void relayout()
  {
    helm.setBounds(240, 93, 98 * UI.s, 98 * UI.s);
  }
  
  @Override
  protected void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    
    Graphics2D g2 = (Graphics2D)g;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 

    drawImage(g, UIElements.INVENTORY_BACKGROUND, 0, 0);
    drawImage(g, UIElements.INVENTORY_HEADARMOR, 240, 93);
  }
  
  private void drawImage(Graphics g, String[] key, int x, int y)
  {
    BufferedImage img = App.imageCache.get(key);
    g.drawImage(img, bx + x, by + y, bx + x + img.getWidth(), by + y + img.getHeight(), 0, 0, img.getWidth(), img.getHeight(), null);
  }
}
