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
	public static final String RANDOMCODEKEY= "RANDOMVALIDATECODEKEY";//�ŵ�session�е�key     
    private String randString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";//�������ֻ����ĸ���ַ���
    private int width = 95;// ͼƬ��  
    private int height = 25;// ͼƬ��  
    private int lineSize = 40;// ����������  
    private int stringNum = 4;// ��������ַ�����  
       
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
	        // BufferedImage���Ǿ��л�������Image��,Image������������ͼ����Ϣ���� ������ָ����С��ͼƬ
	        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
	        Graphics g = image.getGraphics();// ����Image�����Graphics����,�Ķ��������ͼ���Ͻ��и��ֻ��Ʋ���  
	        //�����ĵ�Ĭ����ɫ�ǰ�ɫ
	        g.fillRect(0, 0, width, height); 
	        //��������
	        g.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, 18)); 
	        //���ø����ߵ���ɫ
	        g.setColor(Color.gray); 
	        // ���Ƹ�����  
	        for (int i = 0; i <= lineSize; i++) { 
	            drawLine(g); 
	        } 
	        // ��������ַ�  
	        String randomString = ""; 
	        for (int i = 1; i <= stringNum; i++) { 
	            randomString = drawString(g, randomString, i); 
	        } 
	        //�����ɵ�����ַ������浽session�У���jsp����ͨ��session.getAttribute("RANDOMCODEKEY")��  
	        //������ɵ���֤�룬Ȼ����û�����Ľ��бȽ�  
	        session.removeAttribute(RANDOMCODEKEY);
	        //����֤����ַ�����ʽ������session����
	        session.setAttribute(RANDOMCODEKEY, randomString);
	        //��ʾ��������
	        g.dispose(); 
	        try { 
	        	 // ��ֹͼ�񻺴档   
	            response.setHeader("Pragma", "no-cache");   
	            response.setHeader("Cache-Control", "no-cache");   
	            response.setDateHeader("Expires", 0);  
	        
	            response.setContentType("image/jpeg");   
	            // ���ڴ��е�ͼƬͨ��������ʽ������ͻ���  
	            ImageIO.write(image, "JPEG", response.getOutputStream()); 
	        } catch (Exception e) { 
	            e.printStackTrace(); 
	        } 
         
         
	}
	//����
	private void drawLine(Graphics g) {
		// TODO Auto-generated method stub
	    int x = random.nextInt(width); 
        int y = random.nextInt(height); 
        int xl = random.nextInt(13); 
        int yl = random.nextInt(15); 
        //�ĸ������ֱ�����һ�����x��y���꣬�ڶ������x��y����
        Graphics2D g2=(Graphics2D)g;
        //�����߶εĴ�ϸ
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
	 * @param g ����
	 * @param randomString ��Ҫ���Ƶ��ַ���
	 * @param i �ǻ��Ƶĵڼ����ַ�
	 * @return
	 */
	private String drawString(Graphics g, String randomString, int i){
		 	g.setFont(getFont()); 
		 	//���û�����ɫ
	        g.setColor(new Color(random.nextInt(101), random.nextInt(111), random 
	                .nextInt(121))); 
	        String rand = String.valueOf(getRandomString(random.nextInt(randString 
	                .length()))); 
	        randomString += rand; 
	        //��������ԭ������Ϊָ����X��Y�㣬ͨ�׵�˵�����´λ������������
	        g.translate(random.nextInt(3), random.nextInt(3)); 
	        //�����ַ�����13����˼����ÿ���ַ��Ŀ�Ȳ��ܳ���13
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
