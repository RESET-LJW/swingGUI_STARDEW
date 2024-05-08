
public class Animal {
	private String species;
	private String speciesEng;
	private String name;
	private int age;
	private int friendshipGrade;
	private int growthStage;
	private int growthPeriod;
	private int productionPeriod;
	private String products;
	private int purchasPrice;
	private int salePrice;
	private int purchaseDay;

	// 종 이름 나이 (임신가능) 우정등급 (기분등급) 성장단계 성장기간 생산기간 생산품 구매가 판매가
	public Animal() {
	}
	
	public Animal(String species, String speciesEng, String name, int age, int friendshipGrade, int growthStage,
			int growthPeriod, int productionPeriod, String products, int purchasPrice, int salePrice, int purchaseDay) {
		super();
		this.species = species;
		this.speciesEng = speciesEng;
		this.name = name;
		this.age = age;
		this.friendshipGrade = friendshipGrade;
		this.growthStage = growthStage;
		this.growthPeriod = growthPeriod;
		this.productionPeriod = productionPeriod;
		this.products = products;
		this.purchasPrice = purchasPrice;
		this.salePrice = salePrice;
		this.purchaseDay = purchaseDay;
	}

	public Animal(String species, String speciesEng, String name, int age, int friendshipGrade, int growthStage, String products, int purchaseDay) {
	    this.species = species;
	    this.speciesEng = speciesEng;
	    this.name = name;
	    this.age = age;
	    this.friendshipGrade = friendshipGrade;
	    this.growthStage = growthStage;
	    this.products = products;
	    this.purchaseDay = purchaseDay;
	}
	
	
	
	public Animal(int animalId, String species, String speciesEng, String name, int age, int friendshipGrade, int growthStage, String products, int purchaseDay) {
	    this.species = species;
	    this.speciesEng = speciesEng;
	    this.name = name;
	    this.age = age;
	    this.friendshipGrade = friendshipGrade;
	    this.growthStage = growthStage;
	    this.products = products;
	    this.purchaseDay = purchaseDay;
	}

	
	public Animal(String species, String speciesEng, int growthPeriod, int productionPeriod, String products,
			int purchasPrice, int salePrice, int purchaseDay) {
		super();
		this.species = species;
		this.speciesEng = speciesEng;
		this.growthPeriod = growthPeriod;
		this.productionPeriod = productionPeriod;
		this.products = products;
		this.purchasPrice = purchasPrice;
		this.salePrice = salePrice;
		this.purchaseDay = purchaseDay;
	}

	public String getSpecies() {
		return species;
	}

	public void setSpecies(String species) {
		this.species = species;
	}

	public String getSpeciesEng() {
		return speciesEng;
	}

	public void setSpeciesEng(String speciesEng) {
		this.speciesEng = speciesEng;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getFriendshipGrade() {
		return friendshipGrade;
	}

	public void setFriendshipGrade(int friendshipGrade) {
		this.friendshipGrade = friendshipGrade;
	}

	public int getGrowthStage() {
		return growthStage;
	}

	public void setGrowthStage(int growthStage) {
		this.growthStage = growthStage;
	}

	public int getGrowthPeriod() {
		return growthPeriod;
	}

	public void setGrowthPeriod(int growthPeriod) {
		this.growthPeriod = growthPeriod;
	}

	public int getProductionPeriod() {
		return productionPeriod;
	}

	public void setProductionPeriod(int productionPeriod) {
		this.productionPeriod = productionPeriod;
	}

	public String getProducts() {
		return products;
	}

	public void setProducts(String products) {
		this.products = products;
	}

	public int getPurchasPrice() {
		return purchasPrice;
	}

	public void setPurchasPrice(int purchasPrice) {
		this.purchasPrice = purchasPrice;
	}

	public int getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(int salePrice) {
		this.salePrice = salePrice;
	}

	public int getPurchaseDay() {
		return purchaseDay;
	}

	public void setPurchaseDay(int purchaseDay) {
		this.purchaseDay = purchaseDay;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + age;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((species == null) ? 0 : species.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Animal other = (Animal) obj;
		if (age != other.age)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (species == null) {
			if (other.species != null)
				return false;
		} else if (!species.equals(other.species))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Animal [species=" + species + ", speciesEng=" + speciesEng + ", name=" + name
				+ ", age=" + age + ", friendshipGrade=" + friendshipGrade + ", growthStage=" + growthStage
				+ ", growthPeriod=" + growthPeriod + ", productionPeriod=" + productionPeriod + ", products=" + products
				+ ", purchasPrice=" + purchasPrice + ", salePrice=" + salePrice + ", purchaseDay=" + purchaseDay + "]";
	}

}