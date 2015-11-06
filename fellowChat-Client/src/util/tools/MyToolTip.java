package util.tools;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.text.View;

import sun.swing.SwingUtilities2;

public class MyToolTip extends JPanel {

	private static final long serialVersionUID = -1405474493135741335L;

	private String text = null; // ��Ҫ��ʾ���ַ���

	private JLayeredPane lp = null; // ����Ҫ�õ��Ĳ�

	public MyToolTip(JLayeredPane lp, String text) {
		this.lp = lp;
		this.text = text;
		this.setOpaque(false);
		lp.add(this, new Integer(JLayeredPane.POPUP_LAYER)); // ��������ڵ������У������Ϳ��Ը������������֮��
		this.setSize(this.getPreferredSize());
		this.setVisible(false); // �������������
	}

	public void setText(String text) {
		this.text = text;
		this.setSize(this.getPreferredSize()); // �ı����ֺ���Ҫ���¼���Size
	}

	public String getText() {
		return text;
	}

	public JLayeredPane getLp() {
		return lp;
	}

	public void setLp(JLayeredPane lp) {
		this.lp = lp;
	}

	// ������������
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(new Color(236,236,234)); // ������ɫ
		g2d.fill(this.getArea(this.getSize()));
		g2d.setColor(new Color(247, 64, 234)); // ������ɫ
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_OFF);
		g2d.drawString(text, 25 / 2, (getHeight() - 10) / 2 + 5);
	}

	// ���߿�
	protected void paintBorder(Graphics g) {
		super.paintBorder(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(new Color(95, 145, 145)); // ������ɫ
		g2d.draw(this.getArea(this.getSize()));
	}

	/**
	 * �����ʺϵ�Size
	 */
	public Dimension getPreferredSize() {
		Font font = getFont();
		FontMetrics fm = getFontMetrics(font);
		Insets insets = getInsets();
		Dimension prefSize = new Dimension(insets.left + insets.right,
				insets.top + insets.bottom);
		if ((text == null) || text.equals("")) {
			text = "";
		} else {
			View v = (this != null) ? (View) getClientProperty("html")
					: null;
			if (v != null) {
				prefSize.width += (int) v.getPreferredSpan(View.X_AXIS);
				prefSize.height += (int) v.getPreferredSpan(View.Y_AXIS);
			} else {
				prefSize.width += SwingUtilities2.stringWidth(this, fm,
						text) + 25; // 25Ϊ��ӵĲ���
				prefSize.height += fm.getHeight() + 10; // 10Ϊ��ӵĲ���
			}
		}
		return prefSize;
	}

	// ���ػ�ͼ����Ҫ������
	private Area getArea(Dimension dim) {
		
		Shape r = new RoundRectangle2D.Float(0, 0, dim.width - 1,
				dim.height - 10, 5, 5); // Բ�Ǿ���
		Area area = new Area(r);
		Polygon polygon = new Polygon(); // �����
		polygon.addPoint(dim.width - 15, dim.height - 10);
		polygon.addPoint(dim.width - 5, dim.height - 10);
		polygon.addPoint(dim.width, dim.height);
		 // �ϲ�
		area.add(new Area(polygon));
		return area; // ����
	}

}