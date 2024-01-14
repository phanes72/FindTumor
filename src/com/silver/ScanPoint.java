package com.silver;

import java.awt.geom.Point2D;


public class ScanPoint {
	private String letterName = new String();
	private Point2D mriScanPoint = new Point2D.Double();
	private boolean letterBlock;
	private String groupName = new String();

	
	public ScanPoint() {
		super();
	}


	
	public ScanPoint(String letterName, Point2D mriScanPoint, boolean letterBlock, String groupName) {
		super();
		this.letterName = letterName;
		this.mriScanPoint = mriScanPoint;
		this.letterBlock = letterBlock;
		this.groupName = groupName;
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

	
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	


}
