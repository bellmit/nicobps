package obps.util.common;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.awt.*;
import java.util.*;
import java.awt.geom.AffineTransform;
import java.security.SecureRandom;
import javax.imageio.IIOImage;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;

public class Captcha extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException 
    {
    	response.setDateHeader("Max-Age", 0);
    	response.setHeader("Pragma", "no-cache");
    	response.setHeader("Cache-Control", "no-cache");
        try 
        {
            Color backgroundColor = Color.white;
            Color textColor = Color.BLACK;
            Color circleColor = Color.GRAY;
            Font textFont = new Font("Arial", Font.PLAIN, 24);
            int charsToPrint = 6;

            int width = 120;
            int height = 30;

            int circlesToDraw = 8;
            float horizMargin = 20.0f;
            float imageQuality = 1;
            double rotationRange = 0.7;

            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = (Graphics2D) bufferedImage.getGraphics();

            g.setColor(backgroundColor);
            g.fillRect(0, 0, width, height);
            g.setColor(circleColor);
                       
            for (int i = 0; i < circlesToDraw; i++) {
                g.drawLine(i * 19, 0, i * 19, height);
                g.drawRect(0, 0, width - 1, height - 1);
            }

            g.setColor(textColor);
            g.setFont(textFont);

            FontMetrics fontMetrics = g.getFontMetrics();
            int maxAdvance = fontMetrics.getMaxAdvance();
            int fontHeight = fontMetrics.getHeight();

            String elegibleChars = "A1B2C3D4E5F6G7H8J9K0L1M2N3P4Q5R6S7T8U9V0W1X2Y3a4b5c6d7e8f9g0h1k2m3n4p5q6r7s8t9u0v1w2x3y4";
            char[] chars = elegibleChars.toCharArray();

            float spaceForLetters = -horizMargin * 2 + width;
            float spacePerChar = spaceForLetters / (charsToPrint - 1.0f);

            StringBuffer finalString = new StringBuffer();
            for (int i = 0; i < charsToPrint; i++) 
            {
                double randomValue = myRandom();
                int randomIndex = (int) Math.round(randomValue * (chars.length - 1));
                char characterToShow = chars[randomIndex];
                finalString.append(characterToShow);

                int charWidth = fontMetrics.charWidth(characterToShow);
                int charDim = Math.max(maxAdvance, fontHeight);
                int halfCharDim = (int) (charDim / 2);

                BufferedImage charImage = new BufferedImage(charDim, charDim, BufferedImage.TYPE_INT_ARGB);
                Graphics2D charGraphics = charImage.createGraphics();
                charGraphics.translate(halfCharDim, halfCharDim);
                double angle = (myRandom() - 0.5) * rotationRange;
                charGraphics.transform(AffineTransform.getRotateInstance(angle));
                charGraphics.translate(-halfCharDim, -halfCharDim);
                charGraphics.setColor(textColor);
                charGraphics.setFont(textFont);

                int charX = (int) (0.5 * charDim - 0.5 * charWidth);
                charGraphics.drawString("" + characterToShow, charX, (int) ((charDim - fontMetrics.getAscent()) / 2 + fontMetrics.getAscent()));

                float x = horizMargin + spacePerChar * (i) - charDim / 2.0f;
                int y = (int) ((height - charDim) / 2);

                g.drawImage(charImage, (int) x, y, charDim, charDim, null, null);
                charGraphics.dispose();

                charImage = null;
                charGraphics = null;

            }

            Iterator iter = ImageIO.getImageWritersByFormatName("JPG");
            if (iter.hasNext()) 
            {
                ImageWriter writer = (ImageWriter) iter.next();
                ImageWriteParam iwp = writer.getDefaultWriteParam();
                iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                iwp.setCompressionQuality(imageQuality);
                writer.setOutput(ImageIO.createImageOutputStream(response.getOutputStream()));
                IIOImage imageIO = new IIOImage(bufferedImage, null, null);
                if(finalString.length()==6){
                    writer.write(null, imageIO, iwp);
                }
                writer = null;
                iwp = null;
                imageIO = null;
            } else {
                throw new RuntimeException("no encoder found for jsp");
            }

            HttpSession session = request.getSession();
            if(session!=null || finalString.length()==6){
                session.removeAttribute("CAPTCHA_KEY");
                session.setAttribute("CAPTCHA_KEY", finalString.toString());
            }

            g.dispose();
            g = null;
            bufferedImage = null;
            textFont=null;
            fontMetrics = null;
            finalString = null;
            iter = null;
        } catch (IOException ioe) {
            //ioe.printStackTrace();
           // throw new RuntimeException("Unable to build image", ioe);
        }

    }
    
    public static double myRandom(){
        SecureRandom number = new SecureRandom();
        String num="0.";
        for (int i = 0; i < 20; i++) {          
          num += number.nextInt(21);         
        }        
        return Double.valueOf(num);
    }
    

}
