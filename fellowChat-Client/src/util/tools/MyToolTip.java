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

	private String text = null; // 将要显示的字符串

	private JLayeredPane lp = null; // 我们要用到的层

	public MyToolTip(JLayeredPane lp, String text) {
		this.lp = lp;
		this.text = text;
		this.setOpaque(false);
		lp.add(this, new Integer(JLayeredPane.POPUP_LAYER)); // 将组件放在弹出层中，这样就可以浮现在其它组件之上
		this.setSize(this.getPreferredSize());
		this.setVisible(false); // 设置组件不可视
	}

	public void setText(String text) {
		this.text = text;
		this.setSize(this.getPreferredSize()); // 改变文字后需要重新计算Size
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

	// 画背景和文字
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(new Color(236,236,234)); // 背景颜色
		g2d.fill(this.getArea(this.getSize()));
		g2d.setColor(new Color(247, 64, 234)); // 文字颜色
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_OFF);
		g2d.drawString(text, 25 / 2, (getHeight() - 10) / 2 + 5);
	}

	// 画边框
	protected void paintBorder(Graphics g) {
		super.paintBorder(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(new Color(95, 145, 145)); // 边线颜色
		g2d.draw(this.getArea(this.getSize()));
	}

	/**
	 * 返回适合的Size
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
						text) + 25; // 25为多加的部分
				prefSize.height += fm.getHeight() + 10; // 10为多加的部分
			}
		}
		return prefSize;
	}

	// 返回画图所需要的区域
	private Area getArea(Dimension dim) {
		
		Shape r = new RoundRectangle2D.Float(0, 0, dim.width - 1,
				dim.height - 10, 5, 5); // 圆角矩形
		Area area = new Area(r);
		Polygon polygon = new Polygon(); // 多边形
		polygon.addPoint(dim.width - 15, dim.height - 10);
		polygon.addPoint(dim.width - 5, dim.height - 10);
		polygon.addPoint(dim.width, dim.height);
		 // 合并
		area.add(new Area(polygon));
		return area; // 返回
	}

}