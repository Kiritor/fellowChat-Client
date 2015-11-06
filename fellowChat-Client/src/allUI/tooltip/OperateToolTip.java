package allUI.tooltip;

import allUI.tooltip.ToolTip.ToolTipModel;

public class OperateToolTip {//此类负责对JToolTip的显示与关闭进行控制；

 private static ToolTipModel toolTipModel;

 private static boolean isCanTop;

 private static int xx_Width;

 private static int yy_Height;

 public OperateToolTip(ToolTipModel single, boolean _useTop,int xx,int yy) {
  OperateToolTip.toolTipModel = single;
  OperateToolTip.isCanTop = _useTop;
  OperateToolTip.xx_Width=xx;
  OperateToolTip.yy_Height=yy;
 }

 public static void begin() {
  if (isCanTop) {
   toolTipModel.setAlwaysOnTop(true);
  }
  toolTipModel.setLocation(xx_Width, yy_Height);
  toolTipModel.setVisible(true);
 }

 public static void close() {
  toolTipModel.setVisible(false);
  toolTipModel.dispose();
 }
}


