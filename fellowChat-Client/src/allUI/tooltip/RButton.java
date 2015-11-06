package allUI.tooltip;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
 
 class RButton extends JButton {
  public RButton(String label) {
    super(label);
    // 这些声明把按钮扩展为一个圆而不是一个椭圆。
    Dimension size = getPreferredSize();
    size.width = size.height = Math.max(size.width,
                                        size.height);
    setPreferredSize(size);
    //这个调用使JButton不画背景，而允许我们画一个圆的背景。
    setContentAreaFilled(false);
    this.setBackground(Color.GRAY);
  }
 
  // 画圆的背景和标签
  protected void paintComponent(Graphics g) {
    if (getModel().isArmed()) {
      // 你可以选一个高亮的颜色作为圆形按钮类的属性
      g.setColor(Color.lightGray);
    }
    else {
      g.setColor(getBackground());
    }
    g.fillOval(0, 0, getSize().width - 1,
               getSize().height - 1);
    //这个调用会画一个标签和焦点矩形。
    super.paintComponent(g);
  }
 
  // 用简单的弧画按钮的边界。
  protected void paintBorder(Graphics g) {
    g.setColor(getForeground());
    g.drawOval(0, 0, getSize().width - 1,
               getSize().height - 1);
  }
  // 侦测点击事件
  Shape shape;
  public boolean contains(int x, int y) {
    // 如果按钮改变大小，产生一个新的形状对象。
    if (shape == null ||
        !shape.getBounds().equals(getBounds())) {
      shape = new Ellipse2D.Float(0, 0,
                                  getWidth(), getHeight());
    }
    return shape.contains(x, y);
  }
 
  // 测试程序
  public static void main(String[] args) {
    // 产生一个带‘Jackpot’标签的按钮。
    JButton button = new RButton("Jackpot");
    ImageIcon ic = new ImageIcon("E://clientForMssql//Icons//item_group.gif");
    JButton button2 = new JButton(ic);
    button.setBackground(Color.GRAY);
    // 产生一个框架以显示这个按钮。
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //frame.getContentPane().setBackground(Color.GRAY);
    frame.getContentPane().add(button);
    frame.getContentPane().add(button2);
    frame.getContentPane().setLayout(new FlowLayout());
    frame.setSize(200, 200);
    frame.setVisible(true);
  }
}
 
