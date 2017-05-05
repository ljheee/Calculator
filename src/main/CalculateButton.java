/**
  * @(#)main.CalculateButton.java  2008-8-5  
  * Copy Right Information	: Tarena
  * Project					: Calculator
  * JDK version used		: jdk1.6.4
  * Comments				: 定制自己的按钮类。
  * Version					: 1.0
  * Sr	Date		Modified By		Why & What is modified
  * 1.	2008-8-5 	小猪     		新建
  **/
package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.InputEvent;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.KeyStroke;


 /**
 * 定制自己的按钮类。<br>
 * 练习自己定制按钮。比如定制按钮的高度、宽度、文字等。<br>
 * 2008-8-5
 * @author		达内科技[Tarena Training Group]
 * @version	1.0
 * @since		JDK1.6(建议) 
 * @author		Administrator
 */
public class CalculateButton extends JButton {

	
	/**
	 * 构造一个具有缺省事件、指定文本、指定快捷键的JButton。
	 * @param listener Action事件。
	 * @param text 按钮上的文本。
	 * @param key 快捷键，键值。
	 * @param isctrl 是否键入Ctrl
	 */
	public CalculateButton(Action listener,String text,int key,boolean isctrl) {
		addActionListener(listener);
		if(isctrl)
			getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(key,InputEvent.CTRL_DOWN_MASK),text);
		else
			getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(key,0),text);
		getActionMap().put(text, listener);
		
		setPreferredSize(new Dimension(35,26));
		setForeground(Color.BLUE);
		
		setText(text);
		setFont(new Font("Tahoma",Font.PLAIN,12));
		setMargin(new Insets(0,0,0,0));
	}
	
	/**
	 * 构造一个具有缺省事件、指定文本、指定快捷键、指定文本颜色的JButton。
	 * @param listener Action事件。
	 * @param text 按钮上的文本。
	 * @param key 快捷键，键值。
	 * @param isctrl 是否键入Ctrl
	 * @param color 文本的颜色。
	 */
	public CalculateButton(Action listener,String text,int key,boolean isctrl,Color color) {
		this(listener,text,key,isctrl);
		setForeground(color);
	}
	
	/**
	 * 构造一个具有缺省事件、指定文本、指定快捷键、指定文本颜色的JButton。
	 * @param listener Action事件。
	 * @param text 按钮上的文本。
	 * @param key 快捷键，键值。
	 * @param isctrl 是否键入Ctrl
	 * @param color 文本的颜色。
	 * @param width 按钮的宽度。
	 * @param height 按钮的高度。
	 */
	public CalculateButton(Action listener,String text,int key,boolean isctrl,Color color,int width,int height) {
		this(listener,text,key,isctrl,color);
		setSize(width,height);
		setPreferredSize(new Dimension(width,height));
	}
	
	
	
	/**
	 * 绘制边界。
	 * @param g Grahpics画笔。
	 */
	@Override
	protected void paintBorder(Graphics g) {
		super.paintBorder(g);
		Color c = g.getColor();
		g.setColor(getBackground());
		g.translate(0, 0);
		g.drawRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
		g.setColor(c);
	}
}
