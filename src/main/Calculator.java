/**
  * @(#)main.Calculator.java  2008-8-5  
  * Copy Right Information	: Tarena
  * Project					: Calculator
  * JDK version used		: jdk1.6.4
  * Comments				: 计算器界面类。
  * Version					: 1.0
  * Sr	Date		Modified By		Why & What is modified
  * 1.	2008-8-5 	小猪     		新建
  **/
package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

import about.About;

 /**
 * 计算器界面类。<br>
 * 练习使用ActionListener事件的处理。<br>
 * 2008-8-5
 * @author		达内科技[Tarena Training Group]
 * @version	1.0
 * @since		JDK1.6(建议) 
 */
public class Calculator extends JFrame implements ActionListener{
	
	//==================菜单定义区=======================//
	private JMenuBar menuBar=new JMenuBar();
	private JMenu menuEdit=new JMenu("编辑(E)");
		private JMenuItem menuItemCopy=new JMenuItem("复制(C)",KeyEvent.VK_C);
		private JMenuItem menuItemCut=new JMenuItem("粘贴(P)",KeyEvent.VK_V);
	private JMenu menuView=new JMenu("查看(V)");
		private JRadioButton boxStand=new JRadioButton("标准型(T)",true);
		private JRadioButton boxSience=new JRadioButton("科学型(S)",false);
		private JMenuItem menuItemMath=new JMenuItem("数学分组",KeyEvent.VK_I);
	private JMenu menuHelp=new JMenu("帮助(H)");
		private JMenuItem menuItemHelp=new JMenuItem("帮助主题",KeyEvent.VK_H);
		private JMenuItem menuItemAbout=new JMenuItem("关于计算器",KeyEvent.VK_A);
	//================菜单定义区结束======================//

	
	private JPanel panSave = new JPanel();
	private JPanel panClear = new JPanel();
	private JPanel panBtn = new JPanel();
	
	private JTextField msg = new JTextField("0");
	private CalculateButton[] buttons = new CalculateButton[27];
	private JLabel lblInfo = new JLabel();
	
	/** 保存预执行操作的数 */
	private double value = 0;
	/** 是否已经保存 */
	private boolean hasSaved = false;
	/** 保存的数字 */
	private double saveNum = 0;
	/** 运算操作：0:无操作。1:加。2:减。3:乘。4:除。 */
	private int operation = 0;
	/** 保存临时数据 */
	private StringBuffer tmp = new StringBuffer();
	/** 文字框允许输入的数字长度 */
	private int allowLength = 32;
	
	private boolean hasPressed = false;
	
	/**
	 * 计算器界面类缺省构造函数。
	 */
	public Calculator() {
		setSize(260,240);
		setTitle("计算器");
		Toolkit tk=Toolkit.getDefaultToolkit();
		setLocation((tk.getScreenSize().width-getWidth())/2,(tk.getScreenSize().height-getHeight())/2);
		
		initMenu();
		initButton();
		initAdd();
		
		panSave.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP,0),"UpPress");
		panSave.getActionMap().put("UpPress", new ButtonListener());
		
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	/**
	 * 初始化菜单添加。
	 */
	private void initMenu(){
		//========菜单添加======================
		setJMenuBar(menuBar);
		menuBar.add(menuEdit);
			menuEdit.setMnemonic(KeyEvent.VK_E);
			menuEdit.add(menuItemCopy);
			menuItemCopy.setMnemonic(KeyEvent.VK_C);
			menuEdit.add(menuItemCut);
			menuItemCut.setMnemonic(KeyEvent.VK_P);
		menuBar.add(menuView);
			menuView.setMnemonic(KeyEvent.VK_V);
			ButtonGroup group=new ButtonGroup();
			group.add(boxStand);
			group.add(boxSience);
			menuView.add(boxStand);
			menuView.add(boxSience);
			menuView.addSeparator();
			menuView.add(menuItemMath);
		menuBar.add(menuHelp);
			menuHelp.setMnemonic(KeyEvent.VK_H);
			menuHelp.add(menuItemHelp);
			menuHelp.addSeparator();
			menuHelp.add(menuItemAbout);
		
		menuItemCopy.addActionListener(this);
		menuItemCut.addActionListener(this);
		menuItemAbout.addActionListener(this);	
		//=========菜单添加OK===================
	}
	/**
	 * 初始化按钮。按钮的初始化、部署、事件添加等。
	 */
	private void initButton(){
		ButtonListener listener = new ButtonListener();
		
		buttons[0] = new CalculateButton(listener,"=",KeyEvent.VK_EQUALS,false);
		buttons[1] = new CalculateButton(listener,"+",KeyEvent.VK_ADD,false,Color.RED);
		buttons[2] = new CalculateButton(listener,"-",KeyEvent.VK_SUBTRACT,false,Color.RED);
		buttons[3] = new CalculateButton(listener,"*",KeyEvent.VK_MULTIPLY,false,Color.RED);
		buttons[4] = new CalculateButton(listener,"/",KeyEvent.VK_DIVIDE,false,Color.RED);
		buttons[5] = new CalculateButton(listener,"1/x",KeyEvent.VK_R,false);
		buttons[6] = new CalculateButton(listener,"+/-",KeyEvent.VK_F9,false);
		buttons[7] = new CalculateButton(listener,"sqrt",KeyEvent.VK_EQUALS,false);
		buttons[8] = new CalculateButton(listener,"%",'%',false);
		buttons[9] = new CalculateButton(listener,".",KeyEvent.VK_PERIOD,false);
		
		buttons[10] = new CalculateButton(listener,"0",KeyEvent.VK_0,false);
		buttons[11] = new CalculateButton(listener,"1",KeyEvent.VK_1,false);
		buttons[12] = new CalculateButton(listener,"2",KeyEvent.VK_2,false);
		buttons[13] = new CalculateButton(listener,"3",KeyEvent.VK_3,false);
		buttons[14] = new CalculateButton(listener,"4",KeyEvent.VK_4,false);
		buttons[15] = new CalculateButton(listener,"5",KeyEvent.VK_5,false);
		buttons[16] = new CalculateButton(listener,"6",KeyEvent.VK_6,false);
		buttons[17] = new CalculateButton(listener,"7",KeyEvent.VK_7,false);
		buttons[18] = new CalculateButton(listener,"8",KeyEvent.VK_8,false);
		buttons[19] = new CalculateButton(listener,"9",KeyEvent.VK_9,false);
		
		buttons[20] = new CalculateButton(listener,"MC",KeyEvent.VK_L,true,Color.RED);
		buttons[21] = new CalculateButton(listener,"MR",KeyEvent.VK_R,true,Color.RED);
		buttons[22] = new CalculateButton(listener,"MS",KeyEvent.VK_M,true,Color.RED);
		buttons[23] = new CalculateButton(listener,"M+",KeyEvent.VK_P,true,Color.RED);
		buttons[24] = new CalculateButton(listener,"Backspace",KeyEvent.VK_BACK_SPACE,false,Color.RED,62,25);
		buttons[25] = new CalculateButton(listener,"CE",KeyEvent.VK_DELETE,false,Color.RED,62,25);
		buttons[26] = new CalculateButton(listener,"C",KeyEvent.VK_ESCAPE,false,Color.RED,62,25);
	}
	
	/**
	 * 初始化各个容器的添加。
	 */
	private void initAdd(){
		LineBorder b = new LineBorder(Color.GRAY);
		
		msg.setSize(240,20);
		msg.setLocation(6, 4);
		msg.setEditable(false);
		msg.setBackground(Color.WHITE);
		msg.setMargin(new Insets(0,6,0,8));
		msg.setHorizontalAlignment(JTextField.RIGHT);
		
		lblInfo.setSize(30,26);
		lblInfo.setLocation(8, 30);
		lblInfo.setBorder(new BevelBorder(BevelBorder.LOWERED));
		lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
		
		panSave.setSize(35,150);
		panSave.setLocation(6, 60);
		panSave.setLayout(new FlowLayout(FlowLayout.CENTER,3,3));
		//panSave.setBorder(b);
		//增加MC、MR、MS、M+
		for(int i=20;i<24;i++)
			panSave.add(buttons[i]);
		
		panClear.setSize(200,26);
		panClear.setLocation(45, 30);
		//panClear.setBorder(b);
		panClear.setLayout(new FlowLayout(FlowLayout.CENTER,3,0));
		//增加Backspace、CE、C
		for(int i=24;i<27;i++)
			panClear.add(buttons[i]);
		
		panBtn.setSize(200, 150);
		panBtn.setLocation(45, 60);
		//panBtn.setBorder(b);
		panBtn.setLayout(new FlowLayout(FlowLayout.CENTER,4,3));
		panBtn.add(buttons[17]);//7
		panBtn.add(buttons[18]);//8
		panBtn.add(buttons[19]);//9
		panBtn.add(buttons[4]);//÷
		panBtn.add(buttons[7]);//sqrt
		panBtn.add(buttons[14]);//4
		panBtn.add(buttons[15]);//5
		panBtn.add(buttons[16]);//6
		panBtn.add(buttons[3]);//×
		panBtn.add(buttons[8]);//%
		panBtn.add(buttons[11]);//1
		panBtn.add(buttons[12]);//2
		panBtn.add(buttons[13]);//3
		panBtn.add(buttons[2]);//-
		panBtn.add(buttons[5]);//1/x
		panBtn.add(buttons[10]);//0
		panBtn.add(buttons[6]);//+/- 
		panBtn.add(buttons[9]);//.
		panBtn.add(buttons[1]);//+
		panBtn.add(buttons[0]);//=
		
		setLayout(null);
		add(msg);
		add(lblInfo);
		add(panSave);
		add(panClear);
		add(panBtn);
	}
	
	public static void main(String[] args){
		new Calculator();
	}
	
	/**
	 * 处理按钮事件。
	 * 1.复制事件<br>
	 * 2.粘贴事件<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;此处应用正则表达式(详细请参考类 java.util.regex.Pattern)解析粘贴板中的内容，仅得到内容中最后的数字和小数点。<br>
	 * 3.关于事件<br>
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==menuItemCopy){
			//JOptionPane.showMessageDialog(null, "复制");
			msg.selectAll();
			msg.copy();
		}
		if(e.getSource()==menuItemCut){
			//JOptionPane.showMessageDialog(null, "粘贴");
			try {
				Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
				if(t!=null && t.isDataFlavorSupported(DataFlavor.stringFlavor)){
					//得到剪贴板中的字符串
					String text = t.getTransferData(DataFlavor.stringFlavor).toString();
					//得到 字符串中的 数字和. 得到的是数组。
					String[] sp = text.split("[\\D&&[^.]]");
					//从数组中得到最后一个数字
					if(sp.length>0){
						String str = sp[sp.length-1];
						System.out.println(str);
						//对得到的数字再次以.分割
						String[] sp2 = str.split("\\.");
						System.out.println(sp2.length);
						//对于数组长度大于2的得到后两个数组成的小数
						if(sp2.length>1){
							String x = sp2[sp2.length-2];
							text =( x.equals("")?"0":x)+"."+sp2[sp2.length-1];
						}
						//对于数组长度等于1的直接可以使用
						//else if(sp2.length==1)
						//	text = sp2[sp2.length-1];
						//对于数组长度等于0的
						else
							//text = sp[sp.length-1];
							text = sp2[sp2.length-1];
						msg.setText(text);
					}
					
				}
			} catch (HeadlessException e1) {
				System.out.println("错误:"+e1.getMessage());
			} catch (UnsupportedFlavorException e1) {
				System.out.println("错误:"+e1.getMessage());
			} catch (IOException e1) {
				System.out.println("错误:"+e1.getMessage());
			}
		}
		if(e.getSource()==menuItemAbout){
			//JOptionPane.showMessageDialog(null, "<html><body>程序参与人员 :<br>研发部总监 : 赵德奎<br>分 析 设 计&nbsp; :&nbsp;&nbsp;杨&nbsp;&nbsp;强<br>代 码 编 写&nbsp; :&nbsp;&nbsp;小&nbsp;&nbsp;猪<body></html>");
			new About(this,"关于本计算器",true);
		}
		
	}
	
	/**
	 * 按钮事件。包括+、-、*、/、1/x、数字等按钮事件。<br>
	 * 此类继承AbstractAction
	 * 2008-8-7
	 * @author		达内科技[Tarena Training Group]
	 * @version	1.0
	 * @since		JDK1.6(建议) 
	 */
	private class ButtonListener extends AbstractAction{
		public void actionPerformed(ActionEvent e) {
			//System.out.println("cpem");
			//System.out.println(e.getActionCommand());
			int i= 0;
			//
			for(i=0;i<buttons.length;i++){
				if(e.getActionCommand().equals(buttons[i].getText()))
					break;
			}
			double value2;
			try {
				value2 = Double.parseDouble(msg.getText());
			} catch (NumberFormatException e1) {
				System.out.println("错误:"+e1.getMessage());
				value2 = 0;
			}
			switch(i){
			case 0:		//=
				if(operation!=0){
					double result = 0;
					switch (operation) {
					case 1:
						result = value+value2;
						break;
					case 2:
						result = value-value2;
						break;
					case 3:
						result = value*value2;
						break;
					case 4:
						result = value/value2;
						break;
					}
					showResult(result+"");
					hasPressed = false;
				}
				break;
			case 1:
			case 2:		//+、-、*、/
			case 3:
			case 4:
				if(operation!=0 && hasPressed){
					countResult(value2);
				}
				value = Double.parseDouble(msg.getText());
				if(tmp.length()>0){
					tmp = new StringBuffer();
				}
				operation = i;
				break;
			case 5:		//1/x
				if(value2!=0)
					showResult((1/value2)+"");
				else
					showResult("");
				break;
			case 6:		//+/-
				if(value2!=0)
					showResult((value2*-1)+"");
				break;
			case 7:		//sqrt
				if(value2!=0)
					showResult(Math.sqrt(value2)+"");
				break;
			case 8:		//%
				showResult((value*value2/100)+"");
				break;
			case 9:		//.
				if(isContain(tmp.toString(), "."))
					break;
				if(value2==0)
					tmp.append(0);
				value2 = -1;
			case 10:	//0-9
				if(value2==0)
					break;
			case 11:
			case 12:
			case 13:
			case 14:
			case 15:
			case 16:
			case 17:
			case 18:
			case 19:
				if(tmp.length()<=allowLength){
					tmp.append(buttons[i].getText());
					msg.setText(tmp.toString());
					if(operation!=0 && hasPressed==false)
						hasPressed = true;
				}
				break;
			case 20:	//MC
				if(hasSaved){
					saveNum = 0;
					hasSaved = false;
					lblInfo.setText("");
				}
				break;
			case 21:	//MR
				String result = saveNum+"";
				msg.setText(result.endsWith(".0")?result.substring(0, result.length()-2):result);
				break;
			case 22:	//MS
				if(value2!=0){
					saveNum = value2;
					hasSaved = true;
					lblInfo.setText("M");
				}
				break;
			case 23:	//M+
				if(value2!=0){
					saveNum += value2;
					hasSaved = true;
					lblInfo.setText("M");
				}
				break;
			case 24:	//Backspace
				if(tmp.length()==1)
					showResult("0");
				if(tmp.length()>1){
					tmp.deleteCharAt(tmp.length()-1);
					msg.setText(tmp.toString());
				}
				break;
			case 25:	//CE
				clearAll("0");
				break;
			case 26:	//C
				clearAll("0");
				break;
			}
		}
	}
	
	/**
	 * 检测某字符串中是否包含某个字符串。
	 * @param str 待检测的字符串。
	 * @param ch 要包含的字符串。
	 * @return 包含返回true，否则false。
	 */
	private boolean isContain(String str,String ch){
		return str.indexOf(ch)!=-1;
	}
	
	/**
	 * 设置计算的得到的结果。
	 * @param result 需要设置的结果。
	 */
	private void showResult(String result){
		msg.setText(result.endsWith(".0")?result.substring(0, result.length()-2):result);
		tmp = new StringBuffer();
		//operation = 0;
	}
	
	/**
	 * 清空结果。
	 * @param result 需要设置的值。
	 */
	private void clearAll(String result){
		showResult(result);
		value = 0;
		operation = 0;
		hasPressed = false;
	}
	
	/**
	 * 计算结果并显示结果。
	 * @param value2 第二个操作数。
	 */
	private void countResult(double value2){
		double result = 0;
		switch (operation) {
		case 1:
			result = value+value2;
			break;
		case 2:
			result = value-value2;
			break;
		case 3:
			result = value*value2;
			break;
		case 4:
			result = value/value2;
			break;
		}
		showResult(result+"");
		hasPressed = false;
		value = result;
	}
}
