import java.applet.*;
import java.awt.*;
import java.awt.image.*;

public class grabTest extends Applet {

	Image img;
	Image new_img;
	
	int w = 0; //元になるイメージの横幅を代入する
	int h = 0; //元になるイメージの縦幅を代入する
	
	int pix[]; //元になるイメージを格納するための配列
	int tmp_pix[];
	int new_pix[]; //変更後のイメージを格納するための配列
	
	public void init(){
		
		img = getImage(getCodeBase(),"amekodomo.jpg");
	
		/*
		 * MediaTracker 
		 *メディアオブジェクトの状態を監視する
		 *
		 * - addImage
		 *   メディアトラッカが監視するリストにイメージを追加する
		 * - waitForID
		 *   指定した識別子を持つイメージのロードを開始する
		 */
		
		MediaTracker mt = new MediaTracker(this);
		mt.addImage(img,0);
		
		try{
			mt.waitForID(0);
		} catch (InterruptedException e){
			
		}

		w = img.getWidth(this); //ロードしたイメージの横幅を取得
		h = img.getHeight(this); //ロードしたイメージの縦幅を取得
		
		pix = new int[w*h];
		tmp_pix = new int[w*h];
		new_pix = new int[w*h];
		
		setPix();
		
	}

	void setPix() {
		try{
			
			/*
			 * PixerGrabber
			 * イメージのピクセル抽出(抽出したデータはint型配列に格納される)
			 * 
			 * - grabPixels 
			 *   ピクセルの読み込み
			 */
			
			PixelGrabber pg = new PixelGrabber(img,0,0,w,h,pix,0,w);
			pg.grabPixels();		 
			
		}catch (InterruptedException e){}
		
		/*上下反転*/
		for(int i=1;i<=w*h;i++){
			new_pix[i-1] = pix[w*h-i]; 			
		}
		
		/*左右反転
		for(int i=0;i<h;i++){
			for(int j=0;j<w;j++){
				new_pix[w*i+j] = tmp_pix[w*(i+1)-(j+1)]; 	
			}
		}*/
		
		MemoryImageSource mimg = new MemoryImageSource(w,h,new_pix,0,w);
		new_img = createImage(mimg);
	}
	
	public void paint(Graphics g){
		g.drawImage(img, 0, 0, this);
		g.drawImage(new_img, w+5, 0, this);
	}
	
	
}
