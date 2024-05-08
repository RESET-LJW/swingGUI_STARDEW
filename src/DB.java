import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DB {
	private static Set<String> loadedFurniture = new HashSet<>();

	public static void register() {
		try (Connection conn = MySqlConnectionProvider.getConnection()) {
			int userid = 0;
			// SQL 쿼리 작성
			String sql = "";
			// PreparedStatement 객체 생성
			PreparedStatement stmt = null;
			int rowsInserted = 0;
			ResultSet rs = null;
			// 유저 테이블 초기설정
			String username = TestFrame.username;
			String password = TestFrame.password;
			sql = "INSERT INTO user (username, playtime, day, time, password) value(?,1,1,1,?)";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, username);
			stmt.setString(2, password);
			rowsInserted = stmt.executeUpdate();
			if (rowsInserted > 0) {
				System.out.println("생성 성공");
			}
			// 생성된 user의 userid 가져오기
			sql = "SELECT * from user WHERE username = ? AND password = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, username); // userid 설정
			stmt.setString(2, password); // password 설정
			// SQL 쿼리 실행 및 결과셋 획득
			rs = stmt.executeQuery();
			// 결과 처리
			while (rs.next()) {
				userid = rs.getInt("userid");
				TestFrame.userid = userid;
			}
			// 가구 테이블 초기 설정
			for (Furniture furniture : FurnituresImpl.buyFurnitureList) {
				sql = "INSERT INTO furniture (name, price, block_x, block_y, block_name, block_width, block_height,userid) VALUES (?, ?, ?, ?, ?, ?, ?,?)";
				stmt = conn.prepareStatement(sql);

				stmt.setString(1, furniture.getName());
				stmt.setInt(2, furniture.getPrice());
				stmt.setInt(3, furniture.getBlock().getX());
				stmt.setInt(4, furniture.getBlock().getY());
				stmt.setString(5, furniture.getBlock().getInfo());
				stmt.setInt(6, furniture.getBlock().getSizeX());
				stmt.setInt(7, furniture.getBlock().getSizeY());
				stmt.setInt(8, userid);

				rowsInserted = stmt.executeUpdate();
				if (rowsInserted > 0) {
					System.out.println("가구 정보가 성공적으로 저장되었습니다.");
				}
			}
			// 농장 테이블 초기 설정
			for (int i = 1; i <= 64; i++) {
				sql = "INSERT INTO farm (farmid, randstate, plantstate, userid) value (?,?,?,?)";
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, i);
				stmt.setString(2, "밭");
				stmt.setString(3, "빈땅");
				stmt.setInt(4, userid);
				// SQL 쿼리 실행
				rowsInserted = stmt.executeUpdate();
				if (rowsInserted > 0) {
					System.out.println("생성 성공");
				}
			}
			MySqlConnectionProvider.closeStatement(stmt);
		} catch (SQLException e) {
			// 예외 처리
			e.printStackTrace();
		}
	}

	public static void save() {
		try (Connection conn = MySqlConnectionProvider.getConnection()) {
			// SQL 쿼리 작성
			String sql = "";
			// PreparedStatement 객체 생성
			PreparedStatement stmt = null;
			int rowsInserted = 0;
			int userid = TestFrame.userid; // 고유한 값 TestFrame에서 갖고있어야함
			// 메인 정보 저장
			int playtime = TestFrame.playtime;
			int day = TestFrame.day;
			int time = TestFrame.time;
			sql = "UPDATE user SET playtime = ?, day = ?, time = ? where userid = ?";
			stmt = conn.prepareStatement(sql);

			stmt.setInt(1, playtime);
			stmt.setInt(2, day);
			stmt.setInt(3, time);
			stmt.setInt(4, userid);
			// SQL 쿼리 실행
			rowsInserted = stmt.executeUpdate();
			if (rowsInserted > 0) {
				System.out.println("메인정보 변경 성공");
			}
			// 농장 저장
			int farmid = 0;
			String randstate = "";
			String plantstate = "";
			for (Block b : TestFrame.blockList.get(2)) {
				String info = b.getInfo();
				if (info.contains("밭")) {
					farmid = Integer.valueOf(info.replaceAll("[^0-9]", ""));
					randstate = info.replaceAll("[0-9]", "");
					plantstate = TestFrame.plantMap.get(farmid);

					sql = "UPDATE farm SET randstate = ?, plantstate = ? where farmid = ? AND userid = ?";
					stmt = conn.prepareStatement(sql);

					stmt.setString(1, randstate);
					stmt.setString(2, plantstate);
					stmt.setInt(3, farmid);
					stmt.setInt(4, userid);

					// SQL 쿼리 실행
					rowsInserted = stmt.executeUpdate();
					if (rowsInserted > 0) {
						System.out.println("농장정보 변경 성공");
					}
				}
			}
			// 집 정보 저장
			for (Furniture furniture : FurnituresImpl.buyFurnitureList) {
				// 새로운 가구를 확인하고, 새로 추가되는 경우에는 인설트로 처리합니다.
				if (isNewFurniture(furniture.getName())) {
					sql = "INSERT INTO furniture (name, price, block_x, block_y, block_name, block_width, block_height, userid) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
					stmt = conn.prepareStatement(sql);

					stmt.setString(1, furniture.getName());
					stmt.setInt(2, furniture.getPrice());
					stmt.setInt(3, furniture.getBlock().getX());
					stmt.setInt(4, furniture.getBlock().getY());
					stmt.setString(5, furniture.getBlock().getInfo());
					stmt.setInt(6, furniture.getBlock().getSizeX());
					stmt.setInt(7, furniture.getBlock().getSizeY());
					stmt.setInt(8, userid);

					rowsInserted = stmt.executeUpdate();
					if (rowsInserted > 0) {
						System.out.println("가구 정보가 성공적으로 저장되었습니다.");
					} else {
						for (Furniture furniture2 : FurnituresImpl.buyFurnitureList) {
							sql = "UPDATE furniture SET price = ?, block_x = ?, block_y = ?, block_name = ?, block_width = ?, block_height = ? where name = ? and userid = ?";
							stmt = conn.prepareStatement(sql);

							stmt.setInt(1, furniture2.getPrice());
							stmt.setInt(2, furniture2.getBlock().getX());
							stmt.setInt(3, furniture2.getBlock().getY());
							stmt.setString(4, furniture2.getBlock().getInfo());
							stmt.setInt(5, furniture2.getBlock().getSizeX());
							stmt.setInt(6, furniture2.getBlock().getSizeY());
							stmt.setString(7, furniture2.getName());
							stmt.setInt(8, userid);

							rowsInserted = stmt.executeUpdate();
							if (rowsInserted > 0) {
								System.out.println("가구 정보 변경 성공");
							}
						}

					}
				}
			}
			// 축사 정보 저장
			for (Animal animal : TestFrame.aniImpl.getAllMyAnimal()) {
//				 if (animal.getAnimalId() == 1) {
				// 동물 ID가 0인 경우에는 INSERT 수행

				sql = "INSERT INTO barn_animals (userid, species, species_eng, name, age, friendship_grade, growth_stage, growth_period, production_period, products, purchase_price, sale_price, purchase_day) "
						+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " + "ON DUPLICATE KEY UPDATE "
						+ "species = VALUES(species), species_eng = VALUES(species_eng), name = VALUES(name), age = VALUES(age), "
						+ "friendship_grade = VALUES(friendship_grade), growth_stage = VALUES(growth_stage), growth_period = VALUES(growth_period), "
						+ "production_period = VALUES(production_period), products = VALUES(products), purchase_price = VALUES(purchase_price), "
						+ "sale_price = VALUES(sale_price), purchase_day = VALUES(purchase_day)";

//				sql = "INSERT INTO barn_animals (`userid`, `species`, `species_eng`, `name`, `age`, `friendship_grade`, `growth_stage`, `growth_period`, `production_period`, `products`, `purchase_price`, `sale_price`, `purchase_day`) "
//						+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				stmt = conn.prepareStatement(sql);

				stmt.setInt(1, userid);
				stmt.setString(2, animal.getSpecies());
				stmt.setString(3, animal.getSpeciesEng());
				stmt.setString(4, animal.getName());
				stmt.setInt(5, animal.getAge());
				stmt.setInt(6, animal.getFriendshipGrade());
				stmt.setInt(7, animal.getGrowthStage());
				stmt.setInt(8, animal.getGrowthPeriod());
				stmt.setInt(9, animal.getProductionPeriod());
				stmt.setString(10, animal.getProducts());
				stmt.setInt(11, animal.getPurchasPrice());
				stmt.setInt(12, animal.getSalePrice());
				stmt.setInt(13, animal.getPurchaseDay());

				// SQL 쿼리 실행
				rowsInserted = stmt.executeUpdate();
				if (rowsInserted > 0) {
					System.out.println("동물 정보 변경 성공");
				}
			}
			// 리소스 해제
			MySqlConnectionProvider.closeStatement(stmt);
		} catch (SQLException e) {
			// 예외 처리
			e.printStackTrace();
		}
	}

	public static boolean isExist(String inputname, String inputpassword) {
		try {
			int userid = 0;
			// 데이터베이스와 연결
			Connection conn;
			conn = MySqlConnectionProvider.getConnection();

			// SQL 쿼리 작성
			String sql = "";
			// PreparedStatement 객체 생성
			PreparedStatement stmt = null;
			// ResultSet 객체 생성
			ResultSet rs = null;
			// 유저가 존재하는지 검사, 있다면 로드
			sql = "SELECT * from user WHERE username = ? AND password = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, inputname); // id 검사
			stmt.setString(2, inputpassword); // password 검사
			// SQL 쿼리 실행 및 결과셋 획득
			rs = stmt.executeQuery();
			// 결과 처리
			while (rs.next()) {
				userid = rs.getInt("userid");
				TestFrame.userid = userid;
			}
			if (userid == 0) { // 유저가 존재하지않음
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public static void load(String inputname, String inputpassword) {
		try {
			int userid = 0;
			// 데이터베이스와 연결
			Connection conn = MySqlConnectionProvider.getConnection();
			// SQL 쿼리 작성
			String sql = "";
			// PreparedStatement 객체 생성
			PreparedStatement stmt = null;
			// ResultSet 객체 생성
			ResultSet rs = null;
			// 유저가 존재하는지 검사, 있다면 로드
			sql = "SELECT * from user WHERE username = ? AND password = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, inputname); // id 검사
			stmt.setString(2, inputpassword); // password 검사
			// SQL 쿼리 실행 및 결과셋 획득
			rs = stmt.executeQuery();
			// 결과 처리
			while (rs.next()) {
				userid = rs.getInt("userid");
				TestFrame.userid = userid;
			}
			// 메인 정보 불러오기
			sql = "SELECT * FROM user WHERE userid=?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userid); // userid 특정
			// 결과 처리
			rs = stmt.executeQuery();
			while (rs.next()) {
				String username = rs.getString("username");
				String password = rs.getString("username");
				int day = rs.getInt("day");
				int time = rs.getInt("time");
				int playtime = rs.getInt("playtime");
				TestFrame.username = username;
				TestFrame.password = password;
				TestFrame.day = day;
				TestFrame.time = time;
				TestFrame.playtime = playtime;
			}
			// 농장 정보 불러오기
			System.out.println("농장시작");
			sql = "SELECT farmid, randstate, plantstate FROM farm WHERE userid=?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userid); // userid 특정
			// SQL 쿼리 실행 및 결과셋 획득
			rs = stmt.executeQuery();
			// 결과 처리
			while (rs.next()) {
				int farmid = rs.getInt("farmid");
				String randstate = rs.getString("randstate");
				String plantstate = rs.getString("plantstate");
				// 처리할 작업 수행
				String setInfo = randstate + farmid; // 밭 상태 변경해줘야함
				for (Block b : TestFrame.blockList.get(2)) {
					if (b.getInfo().contains("밭" + farmid)) {
						b.setInfo(setInfo);
						System.out.println(setInfo);
						break;
					}
				}
				TestFrame.plantMap.put(farmid, plantstate);
			}
			System.out.println(TestFrame.plantMap.toString());
			// 집 정보 불러오기
			System.out.println("집시작");
			sql = "SELECT * FROM furniture WHERE userid=?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userid); // userid 특정
			rs = stmt.executeQuery();
			while (rs.next()) {
				String name = rs.getString("name");
				int price = rs.getInt("price");
				int blockX = rs.getInt("block_x");
				int blockY = rs.getInt("block_y");
				String blockName = rs.getString("block_name");
				int blockWidth = rs.getInt("block_width");
				int blockHeight = rs.getInt("block_height");

				Block block = new Block(blockX, blockY, blockName, blockWidth, blockHeight);
				Furniture furniture = new Furniture(name, price, block);
				FurnituresImpl.buyFurnitureList.add(furniture);
				addLoadedFurniture(name);
			}
			// 축사 정보 불러오기
			System.out.println("축사시작");
			sql = "SELECT * FROM barn_animals WHERE userid=?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userid); // userid 설정
			// SQL 쿼리 실행 및 결과셋 획득
			rs = stmt.executeQuery();
			// 결과 처리
			while (rs.next()) {
				// 결과에서 각 필드 값을 가져와서 원하는 작업 수행
				String species = rs.getString("species");
				String speciesEng = rs.getString("species_eng");
				String name = rs.getString("name");
				int age = rs.getInt("age");
				int friendshipGrade = rs.getInt("friendship_grade");
				int growthStage = rs.getInt("growth_stage");
				int growthPeriod = rs.getInt("growth_period");
				int productionPeriod = rs.getInt("production_period");
				String products = rs.getString("products");
				int purchasPrice = rs.getInt("purchase_price");
				int salePrice = rs.getInt("sale_price");
				int purchaseDay = rs.getInt("purchase_day");

				// Animal 객체 생성
				Animal animal = new Animal(species, speciesEng, name, age, friendshipGrade, growthStage, growthPeriod,
						productionPeriod, products, purchasPrice, salePrice, purchaseDay);
				TestFrame.aniImpl.addMyAnimal(animal);
			}
			// 리소스 해제
			MySqlConnectionProvider.closeResultSet(rs);
			MySqlConnectionProvider.closeStatement(stmt);
			MySqlConnectionProvider.closeConnection(conn);
		} catch (SQLException e) {
			// 예외 처리
			e.printStackTrace();
		}
		System.out.println("로드확인!!");
	}

	// 이미 로드된 가구 목록에 가구 추가
	public static void addLoadedFurniture(String furnitureName) {
		loadedFurniture.add(furnitureName);
	}

	// 새로운 가구인지 확인
	public static boolean isNewFurniture(String furnitureName) {
		return !loadedFurniture.contains(furnitureName);
	}
}
