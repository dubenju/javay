package javay.http;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

public class JavayHttp {
	private String urlAddr = "";
	public JavayHttp(String url ) {
		this.urlAddr = url;
	}
		
	public String getVersion() {
		String strRes = "";
		int byteread = 0;
		URL url = null;
		try {
			url = new URL(this.urlAddr);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return strRes;
		}
		URLConnection conn = null;
		try {
			conn = url.openConnection();
		} catch (IOException e) {
			e.printStackTrace();
			return strRes;
		}
		if (conn instanceof HttpsURLConnection) {
			System.out.println("read by https.");
			InputStream inStream = null;
			try {
				inStream = conn.getInputStream();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (inStream != null) {
				byte[] buffer = new byte[1204];
				try {
					while ((byteread = inStream.read(buffer)) != -1) {
						String line = new String(buffer);
						System.out.println("read internet:" + line + "byte:" + byteread);
						int pos = line.indexOf(":");
						if (pos >= 0) {
							strRes = line.substring(pos + 1);
							break;
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (strRes.length() > 0) {
				System.out.println("return:" + strRes);
				return strRes;
			}
		}
		if (conn instanceof HttpURLConnection) {
			System.out.println("read by http.");
			InputStream inStream = null;
			try {
				inStream = conn.getInputStream();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (inStream != null) {
				byte[] buffer = new byte[1204];
				try {
					while ((byteread = inStream.read(buffer)) != -1) {
						String line = new String(buffer);
						System.out.println("read internet:" + line + "byte:" + byteread);
						int pos = line.indexOf(":");
						if (pos >= 0) {
							strRes = line.substring(pos + 1);
							break;
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("inStream is null.");
			}
			return strRes;
		}
		return strRes;
	}
	public boolean getChksum() {
		boolean bRes = false;
		return bRes;
	}
	public boolean getModule(String output) {
		boolean bRes = false;
		int byteread = 0;
		URL url = null;
		try {
			url = new URL(this.urlAddr);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return bRes;
		}
		URLConnection conn = null;
		try {
			conn = url.openConnection();
		} catch (IOException e) {
			e.printStackTrace();
			return bRes;
		}
		if (conn instanceof HttpsURLConnection) {
			System.out.println("read by https.");
			InputStream inStream = null;
			try {
				inStream = conn.getInputStream();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (inStream != null) {
				FileOutputStream fs = null;
				try {
					fs = new FileOutputStream(output);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
					return bRes;
				}
				byte[] buffer = new byte[1204];
				try {
					while ((byteread = inStream.read(buffer)) != -1) {
						fs.write(buffer, 0, byteread);
						if (!bRes) {
							bRes = true;
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					fs.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return bRes;
		}
		if (conn instanceof HttpURLConnection) {
			System.out.println("read by http.");
			InputStream inStream = null;
			try {
				inStream = conn.getInputStream();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (inStream != null) {
				FileOutputStream fs = null;
				try {
					fs = new FileOutputStream(output);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
					return bRes;
				}
				byte[] buffer = new byte[1204];
				try {
					while ((byteread = inStream.read(buffer)) != -1) {
						fs.write(buffer, 0, byteread);
						if (!bRes) {
							bRes = true;
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					fs.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("inStream is null.");
			}
		}
		return bRes;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return urlAddr;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.urlAddr = url;
	}
}
