
/*ʵ��ĳЩ����ľ۹��Ч��*/

package util.tools;

import java.awt.AWTEvent;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;

import javax.swing.JComponent;
import javax.swing.JLayer;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.plaf.LayerUI;

public class LoginLayerUI extends LayerUI<JPanel> implements ActionListener {

	private static final long serialVersionUID = 1L;
	private boolean mIsRunning = false;// �����ť֮�����������߳��Ƿ�������
	private boolean mIsFadingOut = false;//�����̵߳�ֹͣ���ʹ���
	private Timer mTimer;// ��ָ��ʱ��������һ������ ActionEvent��

	private int mAngle = 0;//������תʱ����Ƕ�
	private int mFadeCount = 0;//���mFadeLimit��������������תͼ������Ľ����Ч��
	private int mFadeLimit = 30;

	private boolean mActive;// �Ƿ���봰����
	private int mX, mY;// ��¼��������
	/*
	 * ��ȾЧ���Ĺ����л᲻�ϵ���paint
	 */
	public void paint(Graphics g, JComponent c) {
		int w = c.getWidth();//��ȡ���c�Ŀ��
		int h = c.getHeight();
		super.paint(g, c);//���ø����paint�������滭���c

		Graphics2D g2 = (Graphics2D) g.create();
		// ���þ۹�ƣ�Spotlight��Ч��
		if (mActive) {
			// �������򽥱䣬�м�͸����
			java.awt.geom.Point2D center = new java.awt.geom.Point2D.Float(mX,
					mY);
			float radius = 72;
			float[] dist = { 0.0f, 1.0f };
			Color[] colors = { new Color(0.0f, 0.0f, 0.0f, 0.0f), Color.black };
			//RadialGradientPaint ���ṩʹ��Բ�η�����ɫ����ģʽ���ĳһ��״�ķ�ʽ
			RadialGradientPaint p = new RadialGradientPaint(center, radius,
					dist, colors);
			//setPaint:Ϊ Graphics2D ���������� Paint ����
			g2.setPaint(p);
			/*Ϊ Graphics2D ���������� Composite�� 
			 *Composite �������л��Ʒ����У��� drawImage��drawString��draw �� fill
			 *����ʹ��AlphaComposite ��ʵ��һЩ������ alpha �ϳɹ���
			 *��Դɫ��Ŀ��ɫ��ϣ���ͼ�κ�ͼ����ʵ�ֻ�Ϻ�͸��Ч��
			 */
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
					.6f));
			//����ǻ滭
			g2.fillRect(0, 0, c.getWidth(), c.getHeight());
		}
		
		
		if (!mIsRunning) {
			return;
		}
		float fade = (float) mFadeCount / (float) mFadeLimit;
		//fade�Ĳ�ͬ�γɣ�12��������ղų��ֵĽ���Ч�����͵�¼�ɹ��������ʧ���̵Ľ���Ч��
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				.5f * fade));
		g2.fillRect(0, 0, w, h);

		int s = Math.min(w, h) / 5;
		int cx = w / 2;
		int cy = h / 2;
		/* Ϊ�����㷨���õ�����ѡ���ֵ��
		 * RenderingHints.KEY_ANTIALIASING:�������ʾ����
		 * RenderingHints.VALUE_ANTIALIAS_ON���������ʾֵ����ʹ�ÿ����ģʽ��ɳ���
		 */
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		/*
		 *  setStroke:���ó��ֹ�����Ҫ����ͼ��
		 * BasicStroke �ඨ�����ͼ��ͼԪ�����������Ե�һ����������
		 */
		g2.setStroke(new BasicStroke(s / 4, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND));
		//���û滭��ɫ
		g2.setPaint(new Color(190,176,150));
		/* rotate����תת������
		 * ��һ������ Math.PI * mAngle / 180����ת�ĽǶȣ��Ի���Ϊ��λ
		 * �ڶ������� cx��x - ��תԭ��� x ����
		 * �ڶ������� cy��y - ��תԭ��� y ����
		 */
		g2.rotate(Math.PI * mAngle / 180, cx, cy);
		
		//12���룬���������ɫ�ǲ�ͬ�ģ�������֮���γɽ����Ч��
		for (int i = 0; i < 12; i++) {
			float scale = (11.0f - (float) i) / 11.0f;
			g2.drawLine(cx + s, cy, cx + s * 2, cy);
			g2.rotate(-Math.PI / 6, cx, cy);
			//ͨ������AlphaComposite.getInstance()�ڶ��������Ĳ�ͬ��ʵ��12���롱��ɫ�Ĳ�ͬ
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
					scale * fade));
		}
		//�ͷŴ�ͼ�ε��������Լ���ʹ�õ�����ϵͳ��Դ�� 
		g2.dispose();
	}

	public void installUI(JComponent c) {
		super.installUI(c);
		JLayer jlayer = (JLayer) c;
		// ��������¼�������ƶ��¼�
		jlayer.setLayerEventMask(AWTEvent.MOUSE_EVENT_MASK
				| AWTEvent.MOUSE_MOTION_EVENT_MASK);
	}

	public void uninstallUI(JComponent c) {
		JLayer jlayer = (JLayer) c;
		jlayer.setLayerEventMask(0);
		super.uninstallUI(c);
	}

	// JLayer����ר�õ�����¼�,��installUI�������¼�����uninstallUI�н����¼�
	protected void processMouseEvent(MouseEvent e, JLayer l) {
		if (e.getID() == MouseEvent.MOUSE_ENTERED)
			mActive = true;
		if (e.getID() == MouseEvent.MOUSE_EXITED)
			mActive = false;
		l.repaint();
	}

	// JLayer����ר�õ�����ƶ��¼�����installUI�������¼�����uninstallUI�н����¼�
	// �������������¼��mX��mY��
	protected void processMouseMotionEvent(MouseEvent e, JLayer l) {
		// ȷ������ƶ��¼�����ڲ������λ�á�
		Point p = SwingUtilities
				.convertPoint(e.getComponent(), e.getPoint(), l);
		mX = p.x;
		mY = p.y;
		l.repaint();
	}

	public void actionPerformed(ActionEvent e) {
		if (mIsRunning) {
			//firePropertyChange():֪ͨ���ڲ�״̬�ĸ���
			firePropertyChange("tick", 0, 1);
			mAngle += 3;
			if (mAngle >= 360) {
				mAngle = 0;
			}
			if (mIsFadingOut) {
				if (--mFadeCount == 0) {
					mIsRunning = false;
					mTimer.stop();
				}
			//��ֹAlphaComposite.getInstance()�ĵڶ���������ֵ������Χ
			} else if (mFadeCount < mFadeLimit) {
				mFadeCount++;
			}
		}
	}

	public void start() {
		if (mIsRunning) {
			return;
		}
		mIsRunning = true;
		mIsFadingOut = false;
		mFadeCount = 0;
		int fps = 24;
		int tick = 1000 / fps;
		//ÿtick�������һ�α����е�actionPerformed����
		mTimer = new Timer(tick, this);
		mTimer.start();
	}

	public void stop() {
		mIsFadingOut = true;
	}
	//����repaint()����
	public void applyPropertyChange(PropertyChangeEvent pce, JLayer l) {
		if ("tick".equals(pce.getPropertyName())) {
			l.repaint();
		}
	}
}