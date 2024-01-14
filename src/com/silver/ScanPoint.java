package com.silver;

import java.awt.geom.Point2D;


public class ScanPoint {
	private String letterName = new String();
	private Point2D mriScanPoint = new Point2D.Double();
	private boolean letterBlock;

	
	public ScanPoint() {
		super();
	}

	public ScanPoint(String letterName, Point2D mriScanPoint) {
		super();
		this.letterName = letterName;
		this.mriScanPoint = mriScanPoint;
	}

	
	public String getLetterName() {
		return letterName;
	}

	public void setLetterName(String letterName) {
		this.letterName = letterName;
	}

	
	public Point2D getMriScanPoint() {
		return mriScanPoint;
	}

	public void setMriScanPoint(Point2D mriScanPoint) {
		this.mriScanPoint = mriScanPoint;
	}

	
	public boolean isLetterBlock() {
		return letterBlock;
	}

	public void setLetterBlock(boolean letterBlock) {
		this.letterBlock = letterBlock;
	}


}
