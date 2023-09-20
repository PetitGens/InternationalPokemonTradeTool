package main.java.tradingEngine.gameData;

/**
 * An enumeration used to store information about every Pokémon, like their index number of name in each language.
 * "Missingno" is meant for glitch Pokémon (i.e., those who don't have a legal index number).
 * "BLANK_SPACE" is used as a placeholder for spaces in the porty or in a box.
 * @author Julien Ait azzouzene
 */
public enum Specie {
    MISSINGNO(-1, -1, "MISSINGNO", "MISSINGNO", "MISSINGNO", "MISSINGNO", 0, 0, 0, 0, 0, 0, 0),
    BLANK_SPACE(-2, -2, "", "", "", "", 0, 0, 0, 0, 0, 0, 0),
    BULBASAUR(153, 1, "フシギダネ", "BULBIZARRE", "BISASAM", "이상해씨", 45, 49, 49, 45, 65, 65, 65),
    IVYSAUR(9, 2, "フシギソウ", "HERBIZARRE", "BISAKNOSP", "이상해풀", 60, 62, 63, 60, 80, 80, 80),
    VENUSAUR(154, 3, "フシギバナ", "FLORIZARRE", "BISAFLOR", "이상해꽃", 80, 82, 83, 80, 100, 100, 100),
    CHARMANDER(176, 4, "ヒトカゲ", "SALAMECHE", "GLUMANDA", "파이리", 39, 52, 43, 65, 50, 60, 50),
    CHARMELEON(178, 5, "リザード", "REPTINCEL", "GLUTEXO", "리자드", 58, 64, 58, 80, 65, 80, 65),
    CHARIZARD(180, 6, "リザードン", "DRACAUFEU", "GLURAK", "리자몽", 78, 84, 78, 100, 85, 109, 85),
    SQUIRTLE(177, 7, "ゼニガメ", "CARAPUCE", "SCHIGGY", "꼬부기", 44, 48, 65, 43, 50, 50, 64),
    WARTORTLE(179, 8, "カメール", "CARABAFFE", "SCHILLOK", "어니부기", 59, 63, 80, 58, 65, 65, 80),
    BLASTOISE(28, 9, "カメックス", "TORTANK", "TURTOK", "거북왕", 79, 83, 100, 78, 85, 85, 105),
    CATERPIE(123, 10, "キャタピー", "CHENIPAN", "RAUPY", "캐터피", 45, 30, 35, 45, 20, 20, 20),
    METAPOD(124, 11, "トランセル", "CHRYSACIER", "SAFCON", "단데기", 50, 20, 55, 30, 25, 25, 25),
    BUTTERFREE(125, 12, "バタフリー", "PAPILUSION", "SMETTBO", "버터플", 60, 45, 50, 70, 80, 80, 80),
    WEEDLE(112, 13, "ビードル", "ASPICOT", "HORNLIU", "뿔충이", 40, 35, 30, 50, 20, 20, 20),
    KAKUNA(113, 14, "コクーン", "COCONFORT", "KOKUNA", "딱충이", 45, 25, 50, 35, 25, 25, 25),
    BEEDRILL(114, 15, "スピアー", "DARDARGNAN", "BIBOR", "독침붕", 65, 80, 40, 75, 45, 45, 80),
    PIDGEY(36, 16, "ポッポ", "ROUCOOL", "TAUBSI", "구구", 40, 45, 40, 56, 35, 35, 35),
    PIDGEOTTO(150, 17, "ピジョン", "ROUCOUPS", "TAUBOGA", "피죤", 63, 60, 55, 71, 50, 50, 50),
    PIDGEOT(151, 18, "ピジョット", "ROUCARNAGE", "TAUBOSS", "피죤투", 83, 80, 75, 91, 70, 70, 70),
    RATTATA(165, 19, "コラッタ", "RATTATA", "RATTFRATZ", "꼬렛", 30, 56, 35, 72, 25, 25, 35),
    RATICATE(166, 20, "ラッタ", "RATTATAC", "RATTIKARL", "레트라", 55, 81, 60, 97, 50, 50, 70),
    SPEAROW(5, 21, "オニスズメ", "PIAFABEC", "HABITAK", "깨비참", 40, 60, 30, 70, 31, 31, 31),
    FEAROW(35, 22, "オニドリル", "RAPASDEPIC", "IBITAK", "깨비드릴조", 65, 90, 65, 100, 61, 61, 61),
    EKANS(108, 23, "アーボ", "ABO", "RETTAN", "아보", 35, 60, 44, 55, 40, 40, 54),
    ARBOK(45, 24, "アーボック", "ARBOK", "ARBOK", "아보크", 60, 85, 69, 80, 65, 65, 79),
    PIKACHU(84, 25, "ピカチュウ", "PIKACHU", "PIKACHU", "피카츄", 35, 55, 30, 90, 50, 50, 40),
    RAICHU(85, 26, "ライチュウ", "RAICHU", "RAICHU", "라이츄", 60, 90, 55, 100, 90, 90, 80),
    SANDSHREW(96, 27, "サンド", "SABELETTE", "SANDAN", "모래두지", 50, 75, 85, 40, 30, 20, 30),
    SANDSLASH(97, 28, "サンドパン", "SABLAIREAU", "SANDAMER", "고지", 75, 100, 110, 65, 55, 45, 55),
    NIDORAN_F(15, 29, "ニドラン♀", "NIDORAN♀", "NIDORAN♀", "니드런♀", 55, 47, 52, 41, 40, 40, 40),
    NIDORINA(168, 30, "ニドリーナ", "NIDORINA", "NIDORINA", "니드리나", 70, 62, 67, 56, 55, 55, 55),
    NIDOQUEEN(16, 31, "ニドクイン", "NIDOQUEEN", "NIDOQUEEN", "니드퀸", 90, 82, 87, 76, 75, 75, 85),
    NIDORAN_M(3, 32, "ニドラン♂", "NIDORAN♂", "NIDORAN♂", "니드런♂", 46, 57, 40, 50, 40, 40, 40),
    NIDORINO(167, 33, "ニドリーノ", "NIDORINO", "NIDORINO", "니드리노", 61, 72, 57, 65, 55, 55, 55),
    NIDOKING(7, 34, "ニドキング", "NIDOKING", "NIDOKING", "니드킹", 81, 92, 77, 85, 75, 85, 75),
    CLEFAIRY(4, 35, "ピッピ", "MELOFEE", "PIEPI", "삐삐", 70, 45, 48, 35, 60, 60, 65),
    CLEFABLE(142, 36, "ピクシー", "MELODELFE", "PIXI", "픽시", 95, 70, 73, 60, 85, 85, 90),
    VULPIX(82, 37, "ロコン", "GOUPIX", "VULPIX", "식스테일", 38, 41, 40, 65, 65, 50, 65),
    NINETALES(83, 38, "キュウコン", "FEUNARD", "VULNONA", "나인테일", 73, 76, 75, 100, 100, 81, 100),
    JIGGLYPUFF(100, 39, "プリン", "RONDOUDOU", "PUMMELUFF", "푸린", 115, 45, 20, 20, 25, 45, 25),
    WIGGLYTUFF(101, 40, "プクリン", "GRODOUDOU", "KNUDDELUFF", "푸크린", 140, 70, 45, 45, 50, 75, 50),
    ZUBAT(107, 41, "ズバット", "NOSFERAPTI", "ZUBAT", "주뱃", 40, 45, 35, 55, 40, 30, 40),
    GOLBAT(130, 42, "ゴルバット", "NOSFERALTO", "GOLBAT", "골뱃", 75, 80, 70, 90, 75, 65, 75),
    ODDISH(185, 43, "ナゾノクサ", "MYSTHERBE", "MYRAPLA", "뚜벅쵸", 45, 50, 55, 30, 75, 75, 65),
    GLOOM(186, 44, "クサイハナ", "ORTIDE", "DUFLOR", "냄새꼬", 60, 65, 70, 40, 85, 85, 75),
    VILEPLUME(187, 45, "ラフレシア", "RAFFLESIA", "GIFLOR", "라플레시아", 75, 80, 85, 50, 100, 100, 90),
    PARAS(109, 46, "パラス", "PARAS", "PARAS", "파라스", 35, 70, 55, 25, 55, 45, 55),
    PARASECT(46, 47, "パラセクト", "PARASECT", "PARASEK", "파라섹트", 60, 95, 80, 30, 80, 60, 80),
    VENONAT(65, 48, "コンパン", "MIMITOSS", "BLUZUK", "콘팡", 60, 55, 50, 45, 40, 40, 55),
    VENOMOTH(119, 49, "モルフォン", "AEROMITE", "OMOT", "도나리", 70, 65, 60, 90, 90, 90, 75),
    DIGLETT(59, 50, "ディグダ", "TAUPIQUEUR", "DIGDA", "디그다", 10, 55, 25, 95, 45, 35, 45),
    DUGTRIO(118, 51, "ダグトリオ", "TRIOPIKEUR", "DIGDRI", "닥트리오", 35, 80, 50, 120, 70, 50, 70),
    MEOWTH(77, 52, "ニャース", "MIAOUSS", "MAUZI", "나옹", 40, 45, 35, 90, 40, 40, 40),
    PERSIAN(144, 53, "ペルシアン", "PERSIAN", "SNOBILIKAT", "페르시온", 65, 70, 60, 115, 65, 65, 65),
    PSYDUCK(47, 54, "コダック", "PSYKOKWAK", "ENTON", "고라파덕", 50, 52, 48, 55, 50, 65, 50),
    GOLDUCK(128, 55, "ゴルダック", "AKWAKWAK", "ENTORON", "골덕", 80, 82, 78, 85, 80, 95, 80),
    MANKEY(57, 56, "マンキー", "FEROSINGE", "MENKI", "망키", 40, 80, 35, 70, 35, 35, 45),
    PRIMEAPE(117, 57, "オコリザル", "COLOSSINGE", "RASAFF", "성원숭", 65, 105, 60, 95, 60, 60, 70),
    GROWLITHE(33, 58, "ガーディ", "CANINOS", "FUKANO", "가디", 55, 70, 45, 60, 50, 70, 50),
    ARCANINE(20, 59, "ウインディ", "ARCANIN", "ARKANI", "윈디", 90, 110, 80, 95, 80, 100, 80),
    POLIWAG(71, 60, "ニョロモ", "PTITARD", "QUAPSEL", "발챙이", 40, 50, 40, 90, 40, 40, 40),
    POLIWHIRL(110, 61, "ニョロゾ", "TÊTARTE", "QUAPUTZI", "슈륙챙이", 65, 65, 65, 90, 50, 50, 50),
    POLIWRATH(111, 62, "ニョロボン", "TARTARD", "QUAPPO", "강챙이", 90, 85, 95, 70, 70, 70, 90),
    ABRA(148, 63, "ケーシィ", "ABRA", "ABRA", "캐이시", 25, 20, 15, 90, 105, 105, 55),
    KADABRA(38, 64, "ユンゲラー", "KADABRA", "KADABRA", "윤겔라", 40, 35, 30, 105, 120, 120, 70),
    ALAKAZAM(149, 65, "フーディン", "ALAKAZAM", "SIMSALA", "후딘", 55, 50, 45, 120, 135, 135, 85),
    MACHOP(106, 66, "ワンリキー", "MACHOC", "MACHOLLO", "알통몬", 70, 80, 50, 35, 35, 35, 35),
    MACHOKE(41, 67, "ゴーリキー", "MACHOPEUR", "MASCHOCK", "근육몬", 80, 100, 70, 45, 50, 50, 60),
    MACHAMP(126, 68, "カイリキー", "MACKOGNEUR", "MACHOMEI", "괴력몬", 90, 130, 80, 55, 65, 65, 85),
    BELLSPROUT(188, 69, "マダツボミ", "CHETIFLOR", "KNOFENSA", "모다피", 50, 75, 35, 40, 70, 70, 30),
    WEEPINBELL(189, 70, "ウツドン", "BOUSTIFLOR", "ULTRIGARIA", "우츠동", 65, 90, 50, 55, 85, 85, 45),
    VICTREEBEL(190, 71, "ウツボット", "EMPIFLOR", "SARZENIA", "우츠보트", 80, 105, 65, 70, 100, 100, 60),
    TENTACOOL(24, 72, "メノクラゲ", "TENTACOOL", "TENTACHA", "왕눈해", 40, 40, 35, 70, 100, 50, 100),
    TENTACRUEL(155, 73, "ドククラゲ", "TENTACRUEL", "TENTOXA", "독파리", 80, 70, 65, 100, 120, 80, 120),
    GEODUDE(169, 74, "イシツブテ", "RACAILLOU", "KLEINSTEIN", "꼬마돌", 40, 80, 100, 20, 30, 30, 30),
    GRAVELER(39, 75, "ゴローン", "GRAVALANCH", "GEOROK", "데구리", 55, 95, 115, 35, 45, 45, 45),
    GOLEM(49, 76, "ゴローニャ", "GROLEM", "GEOWAZ", "딱구리", 80, 110, 130, 45, 55, 55, 65),
    PONYTA(163, 77, "ポニータ", "PONYTA", "PONITA", "포니타", 50, 85, 55, 90, 65, 65, 65),
    RAPIDASH(164, 78, "ギャロップ", "GALOPA", "GALLOPA", "날쌩마", 65, 100, 70, 105, 80, 80, 80),
    SLOWPOKE(37, 79, "ヤドン", "RAMOLOSS", "FLEGMON", "야돈", 90, 65, 65, 15, 40, 40, 40),
    SLOWBRO(8, 80, "ヤドラン", "FLAGADOSS", "LAHMUS", "야도란", 95, 75, 110, 30, 80, 100, 80),
    MAGNEMITE(173, 81, "コイル", "MAGNETI", "MAGNETILO", "코일", 25, 35, 70, 45, 95, 95, 55),
    MAGNETON(54, 82, "レアコイル", "MAGNETON", "MAGNETON", "레어코일", 50, 60, 95, 70, 120, 120, 70),
    FARFETCH_D(64, 83, "カモネギ", "CANARTICHO", "PORENTA", "파오리", 52, 65, 55, 60, 58, 58, 62),
    DODUO(70, 84, "ドードー", "DODUO", "DODU", "두두", 35, 85, 45, 75, 35, 35, 35),
    DODRIO(116, 85, "ドードリオ", "DODRIO", "DODRI", "두트리오", 60, 110, 70, 100, 60, 60, 60),
    SEEL(58, 86, "パウワウ", "OTARIA", "JUROB", "쥬쥬", 65, 45, 55, 45, 70, 45, 70),
    DEWGONG(120, 87, "ジュゴン", "LAMANTINE", "JUGONG", "쥬레곤", 90, 70, 80, 70, 95, 70, 95),
    GRIMER(13, 88, "ベトベター", "TADMORV", "SLEIMA", "질퍽이", 80, 80, 50, 25, 40, 40, 50),
    MUK(136, 89, "ベトベトン", "GROTADMORV", "SLEIMOK", "질뻐기", 105, 105, 75, 50, 65, 65, 100),
    SHELLDER(23, 90, "シェルダー", "KOKIYAS", "MUSCHAS", "셀러", 30, 65, 100, 40, 45, 45, 25),
    CLOYSTER(139, 91, "パルシェン", "CRUSTABRI", "AUSTOS", "파르셀", 50, 95, 180, 70, 85, 85, 45),
    GASTLY(25, 92, "ゴース", "FANTOMINUS", "NEBULAK", "고오스", 30, 35, 30, 80, 100, 100, 35),
    HAUNTER(147, 93, "ゴースト", "SPECTRUM", "ALPOLLO", "고우스트", 45, 50, 45, 95, 115, 115, 55),
    GENGAR(14, 94, "ゲンガー", "ECTOPLASMA", "GENGAR", "팬텀", 60, 65, 60, 110, 130, 130, 75),
    ONIX(34, 95, "イワーク", "ONIX", "ONIX", "롱스톤", 35, 45, 160, 70, 30, 30, 45),
    DROWZEE(48, 96, "スリープ", "SOPORIFIK", "TRAUMATO", "슬리프", 60, 48, 45, 42, 90, 43, 90),
    HYPNO(129, 97, "スリーパー", "HYPNOMADE", "HYPNO", "슬리퍼", 85, 73, 70, 67, 115, 73, 115),
    KRABBY(78, 99, "クラブ", "KRABBY", "KRABBY", "크랩", 30, 105, 90, 50, 25, 25, 25),
    KINGLER(138, 98, "キングラー", "KRABBOSS", "KINGLER", "킹크랩", 55, 130, 115, 75, 50, 50, 50),
    VOLTORB(6, 100, "ビリリダマ", "VOLTORBE", "VOLTOBAL", "찌리리공", 40, 30, 50, 100, 55, 55, 55),
    ELECTRODE(141, 101, "マルマイン", "ELECTRODE", "LEKTROBAL", "붐볼", 60, 50, 70, 140, 80, 80, 80),
    EXEGGCUTE(12, 102, "タマタマ", "NOEUNOEUF", "OWEI", "아라리", 60, 40, 80, 40, 60, 60, 45),
    EXEGGUTOR(10, 103, "ナッシー", "NOADKOKO", "KOKOWEI", "나시", 95, 95, 85, 55, 125, 125, 65),
    CUBONE(17, 104, "カラカラ", "OSSELAIT", "TRAGOSSO", "탕구리", 50, 50, 95, 35, 40, 40, 50),
    MAROWAK(145, 105, "ガラガラ", "OSSATUEUR", "KNOGGA", "텅구리", 60, 80, 110, 45, 50, 50, 80),
    HITMONLEE(43, 106, "サワムラー", "KICKLEE", "KICKLEE", "시라소몬", 50, 120, 53, 87, 35, 35, 110),
    HITMONCHAN(44, 107, "エビワラー", "TYGNON", "NOCKCHAN", "홍수몬", 50, 105, 79, 76, 35, 35, 110),
    LICKITUNG(11, 108, "ベロリンガ", "EXCELANGUE", "SCHLURP", "내루미", 90, 55, 75, 30, 60, 60, 75),
    KOFFING(55, 109, "ドガース", "SMOGO", "SMOGON", "또가스", 40, 65, 95, 35, 60, 60, 45),
    WEEZING(143, 110, "マタドガス", "SMOGOGO", "SMOGMOG", "또도가스", 65, 90, 120, 60, 85, 85, 70),
    RHYHORN(18, 111, "サイホーン", "RHINOCORNE", "RIHORN", "뿔카노", 80, 85, 95, 25, 30, 30, 30),
    RHYDON(1, 112, "サイドン", "RHINOFEROS", "RIZEROS", "코뿌리", 105, 130, 120, 40, 45, 45, 45),
    CHANSEY(40, 113, "ラッキー", "LEVEINARD", "CHANEIRA", "럭키", 250, 5, 5, 50, 105, 35, 105),
    TANGELA(30, 114, "モンジャラ", "SAQUEDENEU", "TANGELA", "덩쿠리", 65, 55, 115, 60, 100, 100, 40),
    KANGASKHAN(2, 115, "ガルーラ", "KANGOUREX", "KANGAMA", "캥카", 105, 95, 80, 90, 40, 40, 80),
    HORSEA(92, 116, "タッツー", "HYPOTREMPE", "SEEPER", "쏘드라", 30, 40, 70, 60, 70, 70, 25),
    SEADRA(93, 117, "シードラ", "HYPOCEAN", "SEEMON", "시드라", 55, 65, 95, 85, 95, 95, 45),
    GOLDEEN(157, 118, "トサキント", "POISSIRENE", "GOLDINI", "콘치", 45, 67, 60, 63, 50, 35, 50),
    SEAKING(158, 119, "アズマオウ", "POISSOROY", "GOLKING", "왕콘치", 80, 92, 65, 68, 80, 65, 80),
    STARYU(27, 120, "ヒトデマン", "STARI", "STERNDU", "별가사리", 30, 45, 55, 85, 70, 70, 55),
    STARMIE(152, 121, "スターミー", "STAROSS", "STARMIE", "아쿠스타", 60, 75, 85, 115, 100, 100, 85),
    MR_MIME(42, 122, "バリヤード", "M. MIME", "PANTIMOS", "마임맨", 40, 45, 65, 90, 100, 100, 120),
    SCYTHER(26, 123, "ストライク", "INSECATEUR", "SICHLOR", "스라크", 70, 110, 80, 105, 55, 55, 80),
    JYNX(72, 124, "ルージュラ", "LIPPOUTOU", "ROSSANA", "루주라", 65, 50, 35, 95, 95, 115, 95),
    ELECTABUZZ(53, 125, "エレブー", "ELEKTEK", "ELEKTEK", "에레브", 65, 83, 57, 105, 85, 95, 85),
    MAGMAR(51, 126, "ブーバー", "MAGMAR", "MAGMAR", "마그마", 65, 95, 57, 93, 85, 100, 85),
    PINSIR(29, 127, "カイロス", "SCARABRUTE", "PINSIR", "쁘사이저", 65, 125, 100, 85, 55, 55, 70),
    TAUROS(60, 128, "ケンタロス", "TAUROS", "TAUROS", "켄타로스", 75, 100, 95, 110, 70, 40, 70),
    MAGIKARP(133, 129, "コイキング", "MAGICARPE", "KARPADOR", "잉어킹", 20, 10, 55, 80, 20, 15, 20),
    GYARADOS(22, 130, "ギャラドス", "LEVIATOR", "GARADOS", "갸라도스", 95, 125, 79, 81, 100, 60, 100),
    LAPRAS(19, 131, "ラプラス", "LOKHLASS", "LAPRAS", "라프라스", 130, 85, 80, 60, 95, 85, 95),
    DITTO(76, 132, "メタモン", "METAMORPH", "DITTO", "메타몽", 48, 48, 48, 48, 48, 48, 48),
    EEVEE(102, 133, "イーブイ", "EVOLI", "EVOLI", "이브이", 55, 55, 50, 55, 65, 45, 65),
    VAPOREON(105, 134, "シャワーズ", "AQUALI", "AQUANA", "샤미드", 130, 65, 60, 65, 110, 110, 95),
    JOLTEON(104, 135, "サンダース", "VOLTALI", "BLITZA", "쥬피썬더", 65, 65, 60, 130, 110, 110, 95),
    FLAREON(103, 136, "ブースター", "PYROLI", "FLAMARA", "부스터", 65, 130, 60, 65, 110, 95, 110),
    PORYGON(170, 137, "ポリゴン", "PORYGON", "PORYGON", "폴리곤", 65, 60, 70, 40, 75, 85, 75),
    OMANYTE(98, 138, "オムナイト", "AMONITA", "AMONITAS", "암나이트", 35, 40, 100, 35, 90, 90, 55),
    OMASTAR(99, 139, "オムスター", "AMONISTAR", "AMOROSO", "암스타", 70, 60, 125, 55, 115, 115, 70),
    KABUTO(90, 140, "カブト", "KABUTO", "KABUTO", "투구", 30, 80, 90, 55, 45, 55, 45),
    KABUTOPS(91, 141, "カブトプス", "KABUTOPS", "KABUTOPS", "투구푸스", 60, 115, 105, 80, 70, 65, 70),
    AERODACTYL(171, 142, "プテラ", "PTERA", "AERODACTYL", "프테라", 80, 105, 65, 130, 60, 60, 75),
    SNORLAX(132, 143, "カビゴン", "RONFLEX", "RELAXO", "잠만보", 160, 110, 65, 30, 65, 65, 110),
    ARTICUNO(74, 144, "フリーザー", "ARTIKODIN", "ARKTOS", "프리져", 90, 85, 100, 85, 125, 95, 125),
    ZAPDOS(75, 145, "サンダー", "ELECTHOR", "ZAPDOS", "썬더", 90, 90, 85, 100, 125, 125, 90),
    MOLTRES(73, 146, "ファイヤー", "SULFURA", "LAVADOS", "파이어", 90, 100, 90, 90, 125, 125, 85),
    DRATINI(88, 147, "ミニリュウ", "MINIDRACO", "DRATINI", "미뇽", 41, 64, 45, 50, 50, 50, 50),
    DRAGONAIR(89, 148, "ハクリュー", "DRACO", "DRAGONIR", "신뇽", 61, 84, 65, 70, 70, 70, 70),
    DRAGONITE(66, 149, "カイリュー", "DRACOLOSSE", "DRAGORAN", "망나뇽", 91, 134, 95, 80, 100, 100, 100),
    MEWTWO(131, 150, "ミュウツー", "MEWTWO", "MEWTU", "뮤츠", 106, 110, 90, 130, 154, 154, 90),
    MEW(21, 151, "ミュウ", "MEW", "MEW", "뮤", 100, 100, 100, 100, 100, 100, 100),
    CHIKORITA(-3, 152, "チコリータ", "GERMIGNON", "ENDIVIE", "치코리타", 45, 49, 65, 45, -1, 49, 65),
    BAYLEEF(-3, 153, "ベイリーフ", "MACRONIUM", "LORBLATT", "베이리프", 60, 62, 80, 60, -1, 63, 80),
    MEGANIUM(-3, 154, "メガニウム", "MEGANIUM", "MEGANIE", "메가니움", 80, 82, 100, 80, -1, 83, 100),
    CYNDAQUIL(-3, 155, "ヒノアラシ", "HERICENDRE", "FEURIGEL", "브케인", 39, 52, 43, 65, -1, 60, 50),
    QUILAVA(-3, 156, "マグマラシ", "FEURISSON", "IGELAVAR", "마그케인", 58, 64, 58, 80, -1, 80, 65),
    TYPHLOSION(-3, 157, "バクフーン", "TYPHLOSION", "TORNUPTO", "블레이범", 78, 84, 78, 100, -1, 109, 85),
    TOTODILE(-3, 158, "ワニノコ", "KAIMINUS", "KARNIMANI", "리아코", 50, 65, 64, 43, -1, 44, 48),
    CROCONAW(-3, 159, "アリゲイツ", "CROCRODIL", "TYRACROC", "엘리게이", 65, 80, 80, 58, -1, 59, 63),
    FERALIGATR(-3, 160, "オーダイル", "ALIGATUEUR", "IMPERGATOR", "장크로다일", 85, 105, 100, 78, -1, 79, 83),
    SENTRET(-3, 161, "オタチ", "FOUINETTE", "WIESOR", "꼬리선", 35, 46, 34, 20, -1, 35, 45),
    FURRET(-3, 162, "オオタチ", "FOUINAR", "WIESENIOR", "다꼬리", 85, 76, 64, 90, -1, 45, 55),
    HOOTHOOT(-3, 163, "ホーホー", "HOOTHOOT", "HOOTHOOT", "부우부", 60, 30, 30, 50, -1, 36, 56),
    NOCTOWL(-3, 164, "ヨルノズク", "NOARFANG", "NOCTUH", "야부엉", 100, 50, 50, 70, -1, 76, 96),
    LEDYBA(-3, 165, "レディバ", "COXY", "LEDYBA", "레디바", 40, 20, 30, 55, -1, 40, 80),
    LEDIAN(-3, 166, "レディアン", "COXYCLAQUE", "LEDIAN", "레디안", 55, 35, 50, 85, -1, 55, 110),
    SPINARAK(-3, 167, "イトマル", "MIMIGAL", "WEBARAK", "페이검", 40, 60, 40, 30, -1, 40, 40),
    ARIADOS(-3, 168, "アリアドス", "MIGALOS", "ARIADOS", "아리아도스", 70, 90, 70, 40, -1, 60, 60),
    CROBAT(-3, 169, "クロバット", "NOSTENFER", "IKSBAT", "크로뱃", 85, 90, 80, 130, -1, 70, 80),
    CHINCHOU(-3, 170, "チョンチー", "LOUPIO", "LAMPI", "초라기", 75, 38, 38, 67, -1, 56, 56),
    LANTURN(-3, 171, "ランターン", "LANTURN", "LANTURN", "랜턴", 125, 58, 58, 67, -1, 76, 76),
    PICHU(-3, 172, "ピチュー", "PICHU", "PICHU", "피츄", 20, 40, 15, 60, -1, 35, 35),
    CLEFFA(-3, 173, "ピィ", "MELO", "PII", "삐", 50, 25, 28, 15, -1, 45, 55),
    IGGLYBUFF(-3, 174, "ププリン", "TOUDOUDOU", "FLUFFELUFF", "푸푸린", 90, 30, 15, 15, -1, 40, 20),
    TOGEPI(-3, 175, "トゲピー", "TOGEPI", "TOGEPI", "토게피", 35, 20, 65, 20, -1, 40, 65),
    TOGETIC(-3, 176, "トゲチック", "TOGETIC", "TOGETIC", "토게틱", 55, 40, 85, 40, -1, 80, 105),
    NATU(-3, 177, "ネイティ", "NATU", "NATU", "네이티", 40, 50, 45, 70, -1, 70, 45),
    XATU(-3, 178, "ネイティオ", "XATU", "XATU", "네이티오", 65, 75, 70, 95, -1, 95, 70),
    MAREEP(-3, 179, "メリープ", "WATTOUAT", "VOLTILAMM", "메리프", 55, 40, 40, 35, -1, 65, 45),
    FLAAFFY(-3, 180, "モココ", "LAINERGIE", "WAATY", "보송송", 70, 55, 55, 45, -1, 80, 60),
    AMPHAROS(-3, 181, "デンリュウ", "PHARAMP", "AMPHAROS", "전룡", 90, 75, 75, 55, -1, 115, 90),
    BELLOSSOM(-3, 182, "キレイハナ", "JOLIFLOR", "BLUBELLA", "아르코", 75, 80, 85, 50, -1, 90, 100),
    MARILL(-3, 183, "マリル", "MARILL", "MARILL", "마릴", 70, 20, 50, 40, -1, 20, 50),
    AZUMARILL(-3, 184, "マリルリ", "AZUMARILL", "AZUMARILL", "마릴리", 100, 50, 80, 50, -1, 50, 80),
    SUDOWOODO(-3, 185, "ウソッキー", "SIMULARBRE", "MOGELBAUM", "꼬지모", 70, 100, 115, 30, -1, 30, 65),
    POLITOED(-3, 186, "ニョロトノ", "TARPAUD", "QUAXO", "왕구리", 90, 75, 75, 70, -1, 90, 100),
    HOPPIP(-3, 187, "ハネッコ", "GRANIVOL", "HOPPSPROSS", "통통코", 35, 35, 40, 50, -1, 35, 55),
    SKIPLOOM(-3, 188, "ポポッコ", "FLORAVOL", "HUBELUPF", "두코", 55, 45, 50, 80, -1, 45, 65),
    JUMPLUFF(-3, 189, "ワタッコ", "COTOVOL", "PAPUNGHA", "솜솜코", 75, 55, 70, 110, -1, 55, 85),
    AIPOM(-3, 190, "エイパム", "CAPUMAIN", "GRIFFEL", "에이팜", 55, 70, 55, 85, -1, 40, 55),
    SUNKERN(-3, 191, "ヒマナッツ", "TOURNEGRIN", "SONNKERN", "해너츠", 30, 30, 30, 30, -1, 30, 30),
    SUNFLORA(-3, 192, "キマワリ", "HELIATRONC", "SONNFLORA", "해루미", 75, 75, 55, 30, -1, 105, 85),
    YANMA(-3, 193, "ヤンヤンマ", "YANMA", "YANMA", "왕자리", 65, 65, 45, 95, -1, 75, 45),
    WOOPER(-3, 194, "ウパー", "AXOLOTO", "FELINO", "우파", 55, 45, 45, 15, -1, 25, 25),
    QUAGSIRE(-3, 195, "ヌオー", "MARAISTE", "MORLORD", "누오", 95, 85, 85, 35, -1, 65, 65),
    ESPEON(-3, 196, "エーフィ", "MENTALI", "PSIANA", "에브이", 65, 65, 60, 110, -1, 130, 95),
    UMBREON(-3, 197, "ブラッキー", "NOCTALI", "NACHTARA", "블래키", 95, 65, 110, 65, -1, 60, 130),
    MURKROW(-3, 198, "ヤミカラス", "CORNEBRE", "KRAMURX", "니로우", 60, 85, 42, 91, -1, 85, 42),
    SLOWKING(-3, 199, "ヤドキング", "ROIGADA", "LASCHOKING", "야도킹", 95, 75, 80, 30, -1, 100, 110),
    MISDREAVUS(-3, 200, "ムウマ", "FEUFOREVE", "TRAUNFUGIL", "무우마", 60, 60, 60, 85, -1, 85, 85),
    UNOWN(-3, 201, "アンノーン", "ZARBI", "ICOGNITO", "안농", 48, 72, 48, 48, -1, 72, 48),
    WOBBUFFET(-3, 202, "ソーナンス", "QULBUTOKE", "WOINGENAU", "마자용", 190, 33, 58, 33, -1, 33, 58),
    GIRAFARIG(-3, 203, "キリンリキ", "GIRAFARIG", "GIRAFARIG", "키링키", 70, 80, 65, 85, -1, 90, 65),
    PINECO(-3, 204, "クヌギダマ", "POMDEPIK", "TANNZA", "피콘", 50, 65, 90, 15, -1, 35, 35),
    FORRETRESS(-3, 205, "フォレトス", "FORETRESS", "FORSTELLKA", "쏘콘", 75, 90, 140, 40, -1, 60, 60),
    DUNSPARCE(-3, 206, "ノコッチ", "INSOLOURDO", "DUMMISEL", "노고치", 100, 70, 70, 45, -1, 65, 65),
    GLIGAR(-3, 207, "グライガー", "SCORPLANE", "SKORGLA", "글라이거", 65, 75, 105, 85, -1, 35, 65),
    STEELIX(-3, 208, "ハガネール", "STEELIX", "STAHLOS", "강철톤", 75, 85, 200, 30, -1, 55, 65),
    SNUBBULL(-3, 209, "ブルー", "SNUBBULL", "SNUBBULL", "블루", 60, 80, 50, 30, -1, 40, 40),
    GRANBULL(-3, 210, "グランブル", "GRANBULL", "GRANBULL", "그랑블루", 90, 120, 75, 45, -1, 60, 60),
    QWILFISH(-3, 211, "ハリーセン", "QWILFISH", "BALDORFISH", "침바루", 65, 95, 75, 85, -1, 55, 55),
    SCIZOR(-3, 212, "ハッサム", "CIZAYOX", "SCHEROX", "핫삼", 70, 130, 100, 65, -1, 55, 80),
    SHUCKLE(-3, 213, "ツボツボ", "CARATROC", "POTTROTT", "단단지", 20, 10, 230, 5, -1, 10, 230),
    HERACROSS(-3, 214, "ヘラクロス", "SCARHINO", "SKARABORN", "헤라크로스", 80, 125, 75, 85, -1, 40, 95),
    SNEASEL(-3, 215, "ニューラ", "FARFURET", "SNIEBEL", "포푸니", 55, 95, 55, 115, -1, 35, 75),
    TEDDIURSA(-3, 216, "ヒメグマ", "TEDDIURSA", "TEDDIURSA", "깜지곰", 60, 80, 50, 40, -1, 50, 50),
    URSARING(-3, 217, "リングマ", "URSARING", "URSARING", "링곰", 90, 130, 75, 55, -1, 75, 75),
    SLUGMA(-3, 218, "マグマッグ", "LIMAGMA", "SCHNECKMAG", "마그마그", 40, 40, 40, 20, -1, 70, 40),
    MAGCARGO(-3, 219, "マグカルゴ", "VOLCAROPOD", "MAGCARGO", "마그카르고", 50, 50, 120, 30, -1, 80, 80),
    SWINUB(-3, 220, "ウリムー", "MARCACRIN", "QUIEKEL", "꾸꾸리", 50, 50, 40, 50, -1, 30, 30),
    PILOSWINE(-3, 221, "イノムー", "COCHIGNON", "KEIFEL", "메꾸리", 100, 100, 80, 50, -1, 60, 60),
    CORSOLA(-3, 222, "サニーゴ", "CORAYON", "CORASONN", "코산호", 55, 55, 85, 35, -1, 65, 85),
    REMORAID(-3, 223, "テッポウオ", "REMORAID", "REMORAID", "총어", 35, 65, 35, 65, -1, 65, 35),
    OCTILLERY(-3, 224, "オクタン", "OCTILLERY", "OCTILLERY", "대포무노", 75, 105, 75, 45, -1, 105, 75),
    DELIBIRD(-3, 225, "デリバード", "CADOIZO", "BOTOGEL", "딜리버드", 45, 55, 45, 75, -1, 65, 45),
    MANTINE(-3, 226, "マンタイン", "DEMANTA", "MANTAX", "만타인", 65, 40, 70, 70, -1, 80, 140),
    SKARMORY(-3, 227, "エアームド", "AIRMURE", "PANZAERON", "무장조", 65, 80, 140, 70, -1, 40, 70),
    HOUNDOUR(-3, 228, "デルビル", "MALOSSE", "HUNDUSTER", "델빌", 45, 60, 30, 65, -1, 80, 50),
    HOUNDOOM(-3, 229, "ヘルガー", "DEMOLOSSE", "HUNDEMON", "헬가", 75, 90, 50, 95, -1, 110, 80),
    KINGDRA(-3, 230, "キングドラ", "HYPOROI", "SEEDRAKING", "킹드라", 75, 95, 95, 85, -1, 95, 95),
    PHANPY(-3, 231, "ゴマゾウ", "PHANPY", "PHANPY", "코코리", 90, 60, 60, 40, -1, 40, 40),
    DONPHAN(-3, 232, "ドンファン", "DONPHAN", "DONPHAN", "코리갑", 90, 120, 120, 50, -1, 60, 60),
    PORYGON2(-3, 233, "ポリゴン２", "PORYGON2", "PORYGON2", "폴리곤2", 85, 80, 90, 60, -1, 105, 95),
    STANTLER(-3, 234, "オドシシ", "CERFROUSSE", "DAMHIRPLEX", "노라키", 73, 95, 62, 85, -1, 85, 65),
    SMEARGLE(-3, 235, "ドーブル", "QUEULORIOR", "FARBEAGLE", "루브도", 55, 20, 35, 75, -1, 20, 45),
    TYROGUE(-3, 236, "バルキー", "DEBUGANT", "RABAUZ", "배루키", 35, 35, 35, 35, -1, 35, 35),
    HITMONTOP(-3, 237, "カポエラー", "KAPOERA", "KAPOERA", "카포에라", 50, 95, 95, 70, -1, 35, 110),
    SMOOCHUM(-3, 238, "ムチュール", "LIPPOUTI", "KUSSILLA", "뽀뽀라", 45, 30, 15, 65, -1, 85, 65),
    ELEKID(-3, 239, "エレキッド", "ELEKID", "ELEKID", "에레키드", 45, 63, 37, 95, -1, 65, 55),
    MAGBY(-3, 240, "ブビィ", "MAGBY", "MAGBY", "마그비", 45, 75, 37, 83, -1, 70, 55),
    MILTANK(-3, 241, "ミルタンク", "ECREMEUH", "MILTANK", "밀탱크", 95, 80, 105, 100, -1, 40, 70),
    BLISSEY(-3, 242, "ハピナス", "LEUPHORIE", "HEITEIRA", "해피너스", 255, 10, 10, 55, -1, 75, 135),
    RAIKOU(-3, 243, "ライコウ", "RAIKOU", "RAIKOU", "라이코", 90, 85, 75, 115, -1, 115, 100),
    ENTEI(-3, 244, "エンテイ", "ENTEI", "ENTEI", "앤테이", 115, 115, 85, 100, -1, 90, 75),
    SUICUNE(-3, 245, "スイクン", "SUICUNE", "SUICUNE", "스이쿤", 100, 75, 115, 85, -1, 90, 115),
    LARVITAR(-3, 246, "ヨーギラス", "EMBRYLEX", "LARVITAR", "애버라스", 50, 64, 50, 41, -1, 45, 50),
    PUPITAR(-3, 247, "サナギラス", "YMPHECT", "PUPITAR", "데기라스", 70, 84, 70, 51, -1, 65, 70),
    TYRANITAR(-3, 248, "バンギラス", "TYRANOCIF", "DESPOTAR", "마기라스", 100, 134, 110, 61, -1, 95, 100),
    LUGIA(-3, 249, "ルギア", "LUGIA", "LUGIA", "루기아", 106, 90, 130, 110, -1, 90, 154),
    HO_OH(-3, 250, "ホウオウ", "HO-OH", "HO-OH", "칠색조", 106, 130, 90, 90, -1, 110, 154),
    CELEBI(-3, 251, "セレビィ", "CELEBI", "CELEBI", "세레비", 100, 100, 100, 100, -1, 100, 100),
    ;

    private final int gen1Index;
    private final int gen2Index;
    private String englishName;
    private final String japaneseName;
    private final String frenchName;
    private final String germanName;
    private final String koreanName;

    private final int baseHP;
    private final int baseAttack;
    private final int baseDefense;
    private final int baseSpeed;
    private final int baseSpecial;
    private final int baseSpecialAttack;
    private final int baseSpecialDefense;

    Specie(int gen1Index, int gen2Index, String japaneseName, String frenchName, String germanName, String koreanName,
           int baseHP, int baseAttack, int baseDefense, int baseSpeed, int baseSpecial, int baseSpecialAttack, int baseSpecialDefense) {
        this.gen1Index = gen1Index;
        this.gen2Index = gen2Index;
        this.japaneseName = japaneseName;
        this.frenchName = frenchName;
        this.germanName = germanName;
        this.koreanName = koreanName;
        this.baseHP = baseHP;
        this.baseAttack = baseAttack;
        this.baseDefense = baseDefense;
        this.baseSpeed = baseSpeed;
        this.baseSpecial = baseSpecial;
        this.baseSpecialAttack = baseSpecialAttack;
        this.baseSpecialDefense = baseSpecialDefense;

        setEnglishName();
    }

    /**
     * Some Pokémon have special character in their name (nidoran, farfetch'd, ho-oh, ...) and have to be treated
     * separately
     */
    private void setEnglishName(){
        switch (name()) {
            case "NIDORAN_F" -> englishName = "NIDORAN♀";
            case "NIDORAN_M" -> englishName = "NIDORAN♂";
            case "FARFETCH_D" -> englishName = "FARFETCH'D";
            case "MR_MIME" -> englishName = "MR. MIME";
            case "HO_OH" -> englishName = "HO-OH";
            default -> englishName = name();
        }
    }

    /**
     * Returns the specie's internal number in RBY. This is only exact for valid Gen1 Pokémon as Missingno sees its
     * index set to -1, because handling all the variants of Missingno is boring.
     * @return -> the Gen1 index
     */
    public int getGen1Index() {
        return gen1Index;
    }

    /**
     * Returns the specie's internal number in GSC. This is only exact for valid Gen2 Pokémon as Missingno sees its
     * index set to -1, because handling all the variants of Missingno is boring.
     * @return -> the Gen2 index
     */
    public int getGen2Index() {
        return gen2Index;
    }

    /**
     * Returns the specie's name in english.
     * @return -> the english name
     */
    public String getEnglishName() {
        return englishName;
    }

    /**
     * Returns the specie's name in japanese.
     * @return -> the japanese name
     */
    public String getJapaneseName() {
        return japaneseName;
    }

    /**
     * Returns the specie's name in French.
     * @return -> the French name
     */
    public String getFrenchName() {
        return frenchName;
    }

    /**
     * Returns the specie's name in german.
     * @return -> the german name
     */
    public String getGermanName() {
        return germanName;
    }

    /**
     * Returns the specie's name in korean.
     * @return -> the korean name
     */
    public String getKoreanName() {
        return koreanName;
    }

    /**
     * Returns the specie's base HP stat.
     * @return -> the specie's base HP
     */
    public int getBaseHP() {
        return baseHP;
    }

    /**
     * Returns the specie's base attack stat.
     * @return -> the specie's base attack
     */
    public int getBaseAttack() {
        return baseAttack;
    }

    /**
     * Returns the specie's base defense stat.
     * @return -> the specie's base defense
     */
    public int getBaseDefense() {
        return baseDefense;
    }

    /**
     * Returns the specie's base speed stat.
     * @return -> the specie's base speed
     */
    public int getBaseSpeed() {
        return baseSpeed;
    }

    /**
     * Returns the specie's base special stat. This stat only exists in Gen1 and was back then used as both Special
     * Attack and Special Defense
     * @return -> the specie's base special
     */
    public int getBaseSpecial() {
        return baseSpecial;
    }

    /**
     * Returns the specie's base special attack stat. This stat only exists Gen2 onwards, replacing Gen1 special.
     * @return -> the specie's base special attack
     */
    public int getBaseSpecialAttack() {
        return baseSpecialAttack;
    }

    /**
     * Returns the specie's base special defense stat. This stat only exists Gen2 onwards, replacing Gen1 special.
     * @return -> the specie's base special defense
     */
    public int getBaseSpecialDefense() {
        return baseSpecialDefense;
    }
    
    public String getName(Language language){
        String specieName;
        switch (language){
            case JAPANESE -> specieName = japaneseName;
            case FRENCH -> specieName = frenchName;
            case GERMAN -> specieName = germanName;
            case KOREAN -> specieName = koreanName;
            default -> specieName = englishName;
        }
        return specieName;
    }

    /**
     * Returns the Specie corresponding to the given index number for a Gen1 Pokémon.
     * @param index -> the index number of the wanted Specie
     * @return the corresponding Specie, or MISSINGNO if none was found
     */
    public static Specie specieFromGen1Index(int index){
        for(int i = 0; i < Specie.values().length; i++){
            Specie current_specie = Specie.values()[i];
            if(current_specie.gen1Index == index){
                return current_specie;
            }
        }
        return MISSINGNO;
    }

    /**
     * Returns the Specie corresponding to the given index number for a Gen2 Pokémon.
     * @param index -> the index number of the wanted Specie
     * @return the corresponding Specie, or MISSINGNO if none was found
     */
    public static Specie specieFromGen2Index(int index){
        for(int i = 0; i < Specie.values().length; i++){
            Specie current_specie = Specie.values()[i];
            if(current_specie.gen2Index == index){
                return current_specie;
            }
        }
        return MISSINGNO;
    }
}


