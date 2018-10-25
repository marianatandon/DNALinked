
public class LinkStrand implements IDnaStrand {
	
	private class Node {
		String info;
		Node next;
		public Node(String s) {
			info = s;
			next = null;
		}
	}
	private Node myFirst,myLast;
	private long mySize;
	private int myAppends;
	private Node myCurrent;
	private int myIndex;
	private int myLocalIndex;
	
	public LinkStrand (){
		this("");
	}
	
	public LinkStrand (String s) {
		initialize(s);
	}

	@Override
	public long size() {
		return mySize;
	}

	@Override
	public void initialize(String source) {
		myFirst= new Node(source);
		myLast = myFirst;
		myAppends = 0;
		mySize = source.length();
		myIndex = 0;
		myLocalIndex = 0;
		myCurrent = myFirst;
	}

	@Override
	public IDnaStrand getInstance(String source) {
		return new LinkStrand(source);
	}
	
	public String toString() {
		StringBuilder strand = new StringBuilder();
		Node list = myFirst;
		while (list != null) {
			strand.append(list.info);
			list = list.next;
		}
		return strand.toString();
	}

	@Override
	public IDnaStrand append(String dna) {
		myLast.next = new Node(dna);
		myLast = myLast.next;
		mySize = mySize + dna.length();
		myAppends ++;
		return this;
	}

	@Override
	public IDnaStrand reverse() {
		LinkStrand reverse = new LinkStrand(myFirst.info);
		Node list = myFirst;
		list = list.next;
		while (list != null) {
			Node copy = new Node(list.info);
			copy.next = reverse.myFirst;
			reverse.myFirst = copy;
			list = list.next;
		}
		LinkStrand reversed = new LinkStrand();
		Node lister = reverse.myFirst;
		StringBuilder copy1 = new StringBuilder(lister.info);
		copy1.reverse();
		reversed.initialize(copy1.toString());
		lister = lister.next;
		while (lister != null) {
			StringBuilder copy = new StringBuilder(lister.info);
			copy.reverse();
			reversed.append(copy.toString());
			lister = lister.next;
		}
		return reversed;
	}

	@Override
	public int getAppendCount() {
		return myAppends;
	}

	@Override
	public char charAt(int index) {
		if (index < 0 || index > mySize-1) {
			throw new IndexOutOfBoundsException();
		}
		if (index<myIndex) {
			int gap = index;
			myLocalIndex=0;
			myCurrent = myFirst;
			while (myCurrent.info.length() <= gap ) {
				gap = gap - myCurrent.info.length();
				myCurrent = myCurrent.next;
			}
			myLocalIndex = gap;
			myIndex = index;
			return myCurrent.info.charAt(gap);
		}
		if (myIndex == index) {
			return myCurrent.info.charAt(myLocalIndex);
		}
		if (index > myIndex) {
			int gap = index-myIndex;
			Node exCurrent = myCurrent;
			while(myCurrent.info.length()-myLocalIndex <= gap) {
				gap = gap - (myCurrent.info.length()-myLocalIndex);
				myCurrent = myCurrent.next;
				myLocalIndex = 0; 
			}
			if (exCurrent == myCurrent) {
				myLocalIndex = myLocalIndex + gap;
				myIndex = index;
				return myCurrent.info.charAt(myLocalIndex);
			}
			myLocalIndex =gap;
			myIndex= index;
			return myCurrent.info.charAt(gap);
		}
		return 'a';
		
	}
	

}
