package com.cai.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * ����ͼƬ����ͼ
 * 
 * @author seawind
 * 
 */
public class PicUtils {
	private String srcFile;
	private String destFile;
	private int width;
	private int height;
	private Image img;

	/**
	 * ���캯��
	 * 
	 * @param fileName
	 *            String
	 * @throws IOException
	 */
	public PicUtils(String fileName) throws IOException {
		File _file = new File(fileName); // �����ļ�
		this.srcFile = fileName;
		// �������һ��.
		int index = this.srcFile.lastIndexOf(".");
		String ext = this.srcFile.substring(index);
		this.destFile = this.srcFile.substring(0, index) + "_s" + ext;
		img = javax.imageio.ImageIO.read(_file); // ����Image����
		width = img.getWidth(null); // �õ�Դͼ��
		height = img.getHeight(null); // �õ�Դͼ��
	}

	/**
	 * ǿ��ѹ��/�Ŵ�ͼƬ���̶��Ĵ�С
	 * 
	 * @param w
	 *            int �¿��
	 * @param h
	 *            int �¸߶�
	 * @throws IOException
	 */
	public void resize(int w, int h) throws IOException {
		BufferedImage _image = new BufferedImage(w, h,
				BufferedImage.TYPE_INT_RGB);
		_image.getGraphics().drawImage(img, 0, 0, w, h, null); // ������С���ͼ
		FileOutputStream out = new FileOutputStream(destFile); // ����ļ���
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		encoder.encode(_image); // ��JPEG����
		out.close();
	}

	/**
	 * ���չ̶��ı������ͼƬ
	 * 
	 * @param t
	 *            double ����
	 * @throws IOException
	 */
	public void resize(double t) throws IOException {
		int w = (int) (width * t);
		int h = (int) (height * t);
		resize(w, h);
	}

	/**
	 * �Կ��Ϊ��׼���ȱ������ͼƬ
	 * 
	 * @param w
	 *            int �¿��
	 * @throws IOException
	 */
	public void resizeByWidth(int w) throws IOException {
		int h = (int) (height * w / width);
		resize(w, h);
	}

	/**
	 * �Ը߶�Ϊ��׼���ȱ������ͼƬ
	 * 
	 * @param h
	 *            int �¸߶�
	 * @throws IOException
	 */
	public void resizeByHeight(int h) throws IOException {
		int w = (int) (width * h / height);
		resize(w, h);
	}

	/**
	 * �������߶����ƣ�������ĵȱ�������ͼ
	 * 
	 * @param w
	 *            int �����
	 * @param h
	 *            int ���߶�
	 * @throws IOException
	 */
	public void resizeFix(int w, int h) throws IOException {
		if (width / height > w / h) {
			resizeByWidth(w);
		} else {
			resizeByHeight(h);
		}
	}

	/**
	 * ����Ŀ���ļ��� setDestFile
	 * 
	 * @param fileName
	 *            String �ļ����ַ�
	 */
	public void setDestFile(String fileName) throws Exception {
		if (!fileName.endsWith(".jpg")) {
			throw new Exception("Dest File Must end with \".jpg\".");
		}
		destFile = fileName;
	}

	/**
	 * ��ȡĿ���ļ��� getDestFile
	 */
	public String getDestFile() {
		return destFile;
	}

	/**
	 * ��ȡͼƬԭʼ��� getSrcWidth
	 */
	public int getSrcWidth() {
		return width;
	}

	/**
	 * ��ȡͼƬԭʼ�߶� getSrcHeight
	 */
	public int getSrcHeight() {
		return height;
	}
}
