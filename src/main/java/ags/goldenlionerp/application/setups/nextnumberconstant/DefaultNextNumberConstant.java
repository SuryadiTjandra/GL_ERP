package ags.goldenlionerp.application.setups.nextnumberconstant;

class DefaultNextNumberConstant extends NextNumberConstant {

	static DefaultNextNumberConstant instance = null;
	
	static DefaultNextNumberConstant getInstance() {
		if (instance == null)
			instance = new DefaultNextNumberConstant();
		return instance;
	}
	
	@Override
	public String getDocumentType() {
		return null;
	}

	@Override
	public int getResetNumber() {
		return 0;
	}

	@Override
	public String getResetMethod() {
		return "NR";
	}

	@Override
	public boolean getIncludeMonthInNextNumber() {
		//TODO
		return super.getIncludeMonthInNextNumber();
	}

	@Override
	public boolean getIncludeYearInNextNumber() {
		// TODO
		return super.getIncludeYearInNextNumber();
	}

	@Override
	public String getId() {
		return null;
	}

}
