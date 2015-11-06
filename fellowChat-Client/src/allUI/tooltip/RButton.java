package allUI.tooltip;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
 
 class RButton extends JButton {
  public RButton(String label) {
    super(label);
    // ��Щ�����Ѱ�ť��չΪһ��Բ������һ����Բ��
    Dimension size = getPreferredSize();
    size.width = size.height = Math.max(size.width,
                                        size.height);
    setPreferredSize(size);
    //�������ʹJButton�������������������ǻ�һ��Բ�ı�����
    setContentAreaFilled(false);
    this.setBackground(Color.GRAY);
  }
 
  // ��Բ�ı����ͱ�ǩ
  protected void paintComponent(Graphics g) {
    if (getModel().isArmed()) {
      // �����ѡһ����������ɫ��ΪԲ�ΰ�ť�������
      g.setColor(Color.lightGray);
    }
    else {
      g.setColor(getBackground());
    }
    g.fillOval(0, 0, getSize().width - 1,
               getSize().height - 1);
    //������ûửһ����ǩ�ͽ�����Ρ�
    super.paintComponent(g);
  }
 
  // �ü򵥵Ļ�����ť�ı߽硣
  protected void paintBorder(Graphics g) {
    g.setColor(getForeground());
    g.drawOval(0, 0, getSize().width - 1,
               getSize().height - 1);
  }
  // ������¼�
  Shape shape;
  public boolean contains(int x, int y) {
    // �����ť�ı��С������һ���µ���״����
    if (shape == null ||
        !shape.getBounds().equals(getBounds())) {
      shape = new Ellipse2D.Float(0, 0,
                                  getWidth(), getHeight());
    }
    return shape.contains(x, y);
  }
 
  // ���Գ���
  public static void main(String[] args) {
    // ����һ������Jackpot����ǩ�İ�ť��
    JButton button = new RButton("Jackpot");
    ImageIcon ic = new ImageIcon("E://clientForMssql//Icons//item_group.gif");
    JButton button2 = new JButton(ic);
    button.setBackground(Color.GRAY);
    // ����һ���������ʾ�����ť��
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
 
