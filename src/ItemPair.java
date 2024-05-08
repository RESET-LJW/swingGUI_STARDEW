public class ItemPair<K, C, I> {
	private K key;
	private final C coordinate;
	private final I image;

	public ItemPair(K key, C coordinate, I image) {
		super();
		this.key = key;
		this.coordinate = coordinate;
		this.image = image;
	}

	public K getKey() {
		return key;
	}

	public void setKey(K key) {
		this.key = key;
	}

	public C getCoordinate() {
		return coordinate;
	}

	public I getImage() {
		return image;
	}
	

//	public void setImage(I image) {
//		this.image = image;
//	}

	@Override
	public String toString() {
		return "ItemPair [key=" + key;
	}
	

}