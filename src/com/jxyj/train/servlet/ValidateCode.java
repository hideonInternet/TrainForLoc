package com.jxyj.train.servlet;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ValidateCode
 */
@WebServlet("/ValidateCode")
public class ValidateCode extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Random random=new Random();
	public static final String RANDOMCODEKEY= "RANDOMVALIDATECODEKEY";//放到session中的key     
    private String randString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";//随机产生只有字母的字符串
    private int width = 95;// 图片宽  
    private int height = 25;// 图片高  
    private int lineSize = 40;// 干扰线数量  
    private int stringNum = 4;// 随机产生字符数量  
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ValidateCode() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		   HttpSession session = request.getSession(); 
	        // BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类 ，构造指定大小的图片
	        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
	        Graphics g = image.getGraphics();// 产生Image对象的Graphics对象,改对象可以在图像上进行各种绘制操作  
	        //上下文的默认颜色是白色
	        g.fillRect(0, 0, width, height); 
	        //设置字体
	        g.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, 18)); 
	        //设置干扰线的颜色
	        g.setColor(Color.gray); 
	        // 绘制干扰线  
	        for (int i = 0; i <= lineSize; i++) { 
	            drawLine(g); 
	        } 
	        // 绘制随机字符  
	        String randomString = ""; 
	        for (int i = 1; i <= stringNum; i++) { 
	            randomString = drawString(g, randomString, i); 
	        } 
	        //将生成的随机字符串保存到session中，而jsp界面通过session.getAttribute("RANDOMCODEKEY")，  
	        //获得生成的验证码，然后跟用户输入的进行比较  
	        session.removeAttribute(RANDOMCODEKEY);
	        //将验证码的字符串形式保存在session当中
	        session.setAttribute(RANDOMCODEKEY, randomString);
	        //表示画画结束
	        g.dispose(); 
	        try { 
	        	 // 禁止图像缓存。   
	            response.setHeader("Pragma", "no-cache");   
	            response.setHeader("Cache-Control", "no-cache");   
	            response.setDateHeader("Expires", 0);  
	        
	            response.setContentType("image/jpeg");   
	            // 将内存中的图片通过流动形式输出到客户端  
	            ImageIO.write(image, "JPEG", response.getOutputStream()); 
	        } catch (Exception e) { 
	            e.printStackTrace(); 
	        } 
         
         
	}
	//画线
	private void drawLine(Graphics g) {
		// TODO Auto-generated method stub
	    int x = random.nextInt(width); 
        int y = random.nextInt(height); 
        int xl = random.nextInt(13); 
        int yl = random.nextInt(15); 
        //四个参数分别代表第一个点的x，y坐标，第二个点的x，y坐标
        Graphics2D g2=(Graphics2D)g;
        //设置线段的粗细
        g2.setStroke(new BasicStroke(0.5f));
        g2.drawLine(x, y, x + xl, y + yl); 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	/**
	 * 
	 * @param g 画笔
	 * @param randomString 需要绘制的字符串
	 * @param i 是绘制的第几个字符
	 * @return
	 */
	private String drawString(Graphics g, String randomString, int i){
		 	g.setFont(getFont()); 
		 	//设置画笔颜色
	        g.setColor(new Color(random.nextInt(101), random.nextInt(111), random 
	                .nextInt(121))); 
	        String rand = String.valueOf(getRandomString(random.nextInt(randString 
	                .length()))); 
	        randomString += rand; 
	        //将上下文原点设置为指定的X，Y点，通俗点说就是下次画笔起点是哪里
	        g.translate(random.nextInt(3), random.nextInt(3)); 
	        //绘制字符串，13的意思就是每个字符的宽度不能超过13
	        g.drawString(rand, 13 * i, 16); 
	        return randomString; 
	}

	private String getRandomString(int nextInt) {
		// TODO Auto-generated method stub
		return String.valueOf(randString.charAt(nextInt)); 
	}

	private Font getFont() {
		// TODO Auto-generated method stub
		  return new Font("Fixedsys", Font.CENTER_BASELINE, 18); 
	}
	private Color getRandColor(int fc, int bc) { 
        if (fc > 255) 
            fc = 255; 
        if (bc > 255) 
            bc = 255; 
        int r = fc + random.nextInt(bc - fc - 16); 
        int g = fc + random.nextInt(bc - fc - 14); 
        int b = fc + random.nextInt(bc - fc - 18); 
        return new Color(r, g, b); 
    } 

}
