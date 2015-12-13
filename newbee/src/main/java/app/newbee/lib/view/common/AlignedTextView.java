package app.newbee.lib.view.common;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * 
 * @ClassName: AlignedTextView
 * @Description: 对齐的TextView
 * @author：LiZhimin
 * @date：2015-1-21 下午2:03:29
 * @version V1.0
 */
public class AlignedTextView extends TextView {
	private String TAG = "AlignedTextView";
	private Paint mTextPaint;
	private int mWidth; // 绘制区域的宽度
	private int mHeight; // 绘制区域高度
	private int mPadLeft;// 文字左边距离
	private int mPadTop;// 文字上边距离
	private float mTextSize;// 文字大小
	private String blankStr = "   ";// 表示一个空格的字符串,可用于判断首行缩进
	private float baseSpace = 3.0f;// 文字最小间隔
	private int count;// 记录onDraw执行次数，防止调用setHeight后一直重复执行
	private List<LineBean> mLines = new ArrayList<LineBean>(); // 用于记录每一行的文字

	public AlignedTextView(Context context) {
		super(context);
		init();
	}

	public AlignedTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public AlignedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {
		mTextPaint = getPaint();
		mTextPaint.setAntiAlias(true);
		mTextPaint.setColor(getCurrentTextColor());
		mTextSize = getTextSize();
		mTextPaint.setTextSize(mTextSize);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		mPadLeft = getCompoundPaddingLeft();
		mPadTop = getPaddingTop();
		mWidth = getWidth() - mPadLeft - getCompoundPaddingRight();
		mLines = splitText(getText().toString());
		super.onLayout(changed, left, top, right, bottom);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Drawable backgroudDrawable = getBackground();
		if (backgroudDrawable != null) {
			backgroudDrawable.draw(canvas);
		}
		if (mLines.size() > 0) {
			if (count < 1) {
				count++;
				resetHeight(mLines.size());
			}
			for (int i = 0; i < mLines.size(); i++) {
				String tempText = "";
				LineBean lineBean = mLines.get(i);
				List<String> wordList = lineBean.getWordList();
				for (int j = 0; j < wordList.size(); j++) {
					String word = wordList.get(j);
					float tempTextWidth = mTextPaint.measureText(tempText);
					if (j == 0) {
						canvas.drawText(word, mPadLeft, mPadTop + getLineHeight() * i + mTextSize, mTextPaint);
					} else {
						canvas.drawText(word, mPadLeft + tempTextWidth + lineBean.getSpace() * j, mPadTop
								+ getLineHeight() * i + mTextSize, mTextPaint);
					}
					tempText = tempText + word;
				}
			}

		}
	}

	@Override
	protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
		super.onTextChanged(text, start, lengthBefore, lengthAfter);
		count = 0;
	}

	private void resetHeight(int linesSize) {
		mHeight = getLineHeight() * linesSize + getCompoundPaddingTop() + getCompoundPaddingBottom();
		setHeight(mHeight);
	}

	public ArrayList<LineBean> splitText(String text) {
		ArrayList<String> paraList = splitPara(text);
		ArrayList<LineBean> wordLines = new ArrayList<LineBean>();
		for (int i = 0; i < paraList.size(); i++) {
			wordLines.addAll(splitLine(paraList.get(i)));
		}
		return wordLines;
	}

	/**
	 * 
	 * @MethodName:splitPara
	 * @Description: 分开每个段落
	 */
	public ArrayList<String> splitPara(String text) {
		ArrayList<String> paraList = new ArrayList<String>();
		String[] paraArray = text.split("\\n+");
		for (String para : paraArray) {
			paraList.add(para);
		}
		return paraList;
	}

	/**
	 * 
	 * @MethodName:splitPara
	 * @Description:分开每一行，使每一行填入最多的单词数
	 */
	private ArrayList<LineBean> splitLine(String text) {
		boolean isIndent = text.startsWith(blankStr + blankStr);// TODO:先判断首行是否缩进
		text = text.replaceAll("([\u4e00-\u9fa5])", " $1 ").trim();// 将每一个汉字两端加上空格,再去掉整个字符串两端空格
		String[] wordArray = text.split("\\s+");// 按空格将字符串拆分成数组
		if (isIndent) {
			String[] blankArray = new String[] { blankStr, blankStr };
			String[] tempArray = new String[wordArray.length + 2];
			System.arraycopy(blankArray, 0, tempArray, 0, 2);
			System.arraycopy(wordArray, 0, tempArray, 2, wordArray.length);
			wordArray = tempArray;
		}
		float tempWidth = 0f;
		ArrayList<LineBean> lineList = new ArrayList<LineBean>();
		LineBean lineBean = new LineBean();
		ArrayList<String> wordList = new ArrayList<String>();
		for (int i = 0; i < wordArray.length; i++) {
			String word = wordArray[i];
			float wordWidth = mTextPaint.measureText(word) + baseSpace;
			tempWidth = tempWidth + wordWidth;
			if (tempWidth <= mWidth) {
				wordList.add(word);
			} else {
				lineBean.setWordList(wordList);
				float avgSpace = ((float) mWidth - tempWidth + mTextPaint.measureText(word))
						/ (float) (wordList.size() - 1);
				lineBean.setSpace(avgSpace + baseSpace);
				lineList.add(lineBean);
				// 开始下一行的准备工作
				tempWidth = wordWidth;
				wordList = new ArrayList<String>();
				wordList.add(word);
				lineBean = new LineBean();
			}
			if (i == wordArray.length - 1) {
				lineBean.setWordList(wordList);
				lineList.add(lineBean);
			}
		}
		return lineList;
	}

	/**
	 * 
	 * @ClassName: LineBean
	 * @Description: 记录每一行信息的bean
	 */
	class LineBean {
		private float space = 0f;// 本行文字间距
		private List<String> wordList;// 本行所有文字

		public float getSpace() {
			return space;
		}

		public void setSpace(float space) {
			this.space = space;
		}

		public List<String> getWordList() {
			return wordList;
		}

		public void setWordList(List<String> wordList) {
			this.wordList = wordList;
		}

		@Override
		public String toString() {
			StringBuilder wordStr = new StringBuilder();
			for (int i = 0; i < wordList.size(); i++) {
				wordStr.append(wordList.get(i));
			}
			return "--space：" + space + ":" + wordStr.toString();

		}
	}

}
