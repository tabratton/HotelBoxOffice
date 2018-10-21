--
-- PostgreSQL database dump
--

-- Dumped from database version 10.5
-- Dumped by pg_dump version 10.5

-- Started on 2018-10-20 19:59:15

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 1 (class 3079 OID 12924)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2881 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


--
-- TOC entry 2 (class 3079 OID 16408)
-- Name: uuid-ossp; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS "uuid-ossp" WITH SCHEMA public;


--
-- TOC entry 2882 (class 0 OID 0)
-- Dependencies: 2
-- Name: EXTENSION "uuid-ossp"; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION "uuid-ossp" IS 'generate universally unique identifiers (UUIDs)';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 199 (class 1259 OID 16445)
-- Name: actors; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.actors (
  actor_id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
  actor_name character varying(40) NOT NULL,
  image character varying(40) NOT NULL,
  bio text NOT NULL,
  viewed bigint DEFAULT 0 NOT NULL,
  last_modified timestamp with time zone NOT NULL
);


--
-- TOC entry 200 (class 1259 OID 16460)
-- Name: casting; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.casting (
  casting_id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
  actor_id uuid NOT NULL,
  movie_id uuid NOT NULL,
  character_name character varying(40)
);


--
-- TOC entry 201 (class 1259 OID 16486)
-- Name: customers; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.customers (
  customer_id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
  customer_name character varying(40) NOT NULL,
  pwd character(75) NOT NULL,
  balance numeric NOT NULL,
  room smallint NOT NULL,
  address character varying(40) NOT NULL,
  city character varying(40) NOT NULL,
  zipcode integer NOT NULL,
  us_state character(2) NOT NULL,
  is_admin boolean
);


--
-- TOC entry 197 (class 1259 OID 16419)
-- Name: genres; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.genres (
  genre_id uuid NOT NULL,
  genre_name character varying(40) NOT NULL
);


--
-- TOC entry 198 (class 1259 OID 16424)
-- Name: movies; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.movies (
  movie_id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
  title character varying(100) NOT NULL,
  director character varying(40) NOT NULL,
  description text NOT NULL,
  release_date date NOT NULL,
  image character varying(40) NOT NULL,
  genre_id uuid NOT NULL,
  price numeric NOT NULL,
  viewed bigint DEFAULT 0 NOT NULL,
  last_modified timestamp(4) with time zone NOT NULL
);


--
-- TOC entry 203 (class 1259 OID 16511)
-- Name: ratings; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ratings (
  rating_id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
  customer_id uuid NOT NULL,
  movie_id uuid NOT NULL,
  score smallint NOT NULL,
  last_modified timestamp with time zone NOT NULL
);


--
-- TOC entry 204 (class 1259 OID 16527)
-- Name: rentals; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.rentals (
  rental_id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
  customer_id uuid NOT NULL,
  movie_id uuid NOT NULL,
  rental_date date NOT NULL,
  cost real NOT NULL
);


--
-- TOC entry 202 (class 1259 OID 16504)
-- Name: search_terms; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.search_terms (
  searchterm_id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
  term character varying(40) NOT NULL,
  freq integer DEFAULT 1 NOT NULL
);


--
-- TOC entry 2868 (class 0 OID 16445)
-- Dependencies: 199
-- Data for Name: actors; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.actors (actor_id, actor_name, image, bio, viewed, last_modified) FROM stdin;
d977e8d6-c3f4-4469-b9c4-a6152b273ab2	Felicity Jones	https://i.imgur.com/2sa3kwY.jpg	Felicity Rose Hadley Jones (born 17 October 1983) is an English actress. She started her professional acting career as a child, appearing at age 12 in The Treasure Seekers (1996). She went on to play Ethel Hallow for one season in the television show The Worst Witch and its sequel Weirdsister College. She took time off from acting to attend school during her formative years, and has worked steadily since graduating from Wadham College, Oxford in 2006. On radio, she has played the role of Emma Grundy in the BBC's The Archers. In 2008, she appeared in the Donmar Warehouse production of The Chalk Garden.\r\n\r\nSince 2006 Jones has appeared in numerous films, including Northanger Abbey (2007), Brideshead Revisited (2008), Chéri (2009), The Tempest (2010) and Inferno (2016). She stars in the upcoming Star Wars prequel Rogue One (2016).\r\n\r\nHer performance in the 2011 film Like Crazy was met with critical acclaim, garnering her numerous awards, including a special jury prize at the 2011 Sundance Film Festival. In 2014, her performance as Jane Hawking in The Theory of Everything also met with critical acclaim, garnering her nominations for the Golden Globe, SAG, BAFTA and Academy Award for Best Actress.	0	2018-10-16 00:55:12.794697-04
80a368c1-112e-4c88-bf15-af46d9611fbc	Mads Mikkelsen	https://i.imgur.com/wntU1fk.jpg	Mads Dittmann Mikkelsen (born 22 November 1965) is a Danish actor. Originally a gymnast and dancer, he began his career as an actor in 1996. He rose to fame in Denmark as Tonny the drug dealer in the first two films of the Pusher film trilogy, and in his role as the brash yet sensitive policeman, Allan Fischer, in Peter Thorsboe's Danish television series Rejseholdet (Unit One) (2000–03).\r\n\r\nMikkelsen became more widely known for his role as Tristan in Jerry Bruckheimer's production King Arthur (2004), but achieved worldwide recognition for playing the main antagonist Le Chiffre in the twenty-first James Bond film, Casino Royale (2006). He has since become known for his roles as Igor Stravinsky in Jan Kounen's French film Coco Chanel & Igor Stravinsky (2008) and his Cannes Film Festival Best Actor Award-winning role as Lucas in the 2012 Danish film The Hunt. In 2012, he was voted the Danish American Society's Person of the Year. He starred in the critically acclaimed TV series Hannibal (2013–15) as Dr. Hannibal Lecter. In 2016, Mikkelsen appears as Kaecilius in Marvel's film Doctor Strange, and is also set to appear as Galen Erso in Lucasfilm's Rogue One: A Star Wars Story.\r\n\r\nA. O. Scott of The New York Times remarked that in the Hollywood scene, Mikkelsen has \"become a reliable character actor with an intriguing mug\" but stated that on the domestic front \"he is something else: a star, an axiom, a face of the resurgent Danish cinema\".	1	2018-10-16 00:55:12.794697-04
416930d1-8ce1-469a-8786-9ef616e72d51	Will Smith	https://i.imgur.com/3xGWVcy.jpg	Willard Carroll "Will" Smith Jr. (born September 25, 1968) is an American actor, producer, rapper, and songwriter. In April 2007, Newsweek called him \"the most powerful actor in Hollywood\". Smith has been nominated for five Golden Globe Awards and two Academy Awards, and has won four Grammy Awards.\r\n\r\nIn the late 1980s, Smith achieved modest fame as a rapper under the name The Fresh Prince. In 1990, his popularity increased dramatically when he starred in the popular television series The Fresh Prince of Bel-Air. The show ran for six seasons (1990–96) on NBC and has been syndicated consistently on various networks since then. After the series ended, Smith transitioned from television to film, and ultimately starred in numerous blockbuster films. He is the only actor to have eight consecutive films gross over $100 million in the domestic box office, eleven consecutive films gross over $150 million internationally, and eight consecutive films in which he starred open at the number one spot in the domestic box office tally.\r\n\r\nSmith has been ranked as the most bankable star worldwide by Forbes. As of 2014, 17 of the 21 films in which he has had leading roles have accumulated worldwide gross earnings of over $100 million each, five taking in over $500 million each in global box office receipts. As of 2014, his films have grossed $6.6 billion at the global box office. For his performances as boxer Muhammad Ali in Ali (2001) and stockbroker Chris Gardner in The Pursuit of Happyness (2006), Smith received nominations for the Academy Award for Best Actor.	0	2018-10-20 11:53:11.992793-04
950491ff-b7fb-4e8b-9e25-0b1cd9a1c91b	Forest Whitaker	https://i.imgur.com/7HyDJng.jpg	Forest Steven Whitaker III (born July 15, 1961) is an American actor, director, and producer.\r\n\r\nHe has earned a reputation for intensive character study work for films such as Bird, Platoon, Ghost Dog: The Way of the Samurai and Lee Daniels' The Butler, for his work in independent films and for his recurring role as LAPD Internal Affairs Lieutenant Jon Kavanaugh on the Emmy Award-winning television series The Shield. He is set to portray Saw Gerrera in the Star Wars spin-off film, Rogue One: A Star Wars Story.\r\n\r\nWhitaker won the Academy Award, British Academy Film Award, Golden Globe Award, National Board of Review Award, Screen Actors Guild Award, and various critics groups awards for his performance as Ugandan dictator Idi Amin in the 2006 film The Last King of Scotland.	0	2018-10-16 00:55:12.794697-04
cf733300-bb88-4743-8b40-78ff96d242c0	Diego Luna	https://i.imgur.com/5CEIiw3.jpg	Diego Luna-Alexander (born 29 December 1979) is a Mexican actor known for his childhood telenovela work, a starring role in the film Y tu mamá también and supporting roles in American films, including Rudo y Cursi, The Terminal, Elysium and Milk. He also starred with Romola Garai in Dirty Dancing: Havana Nights and provides the Spanish language narration for the National Geographic Channel documentary Great Migrations.	0	2018-10-16 00:55:12.794697-04
d10c0ab5-1422-4b41-8ba8-9d415c0d7be7	James Earl Jones	https://i.imgur.com/3eR8Ik9.jpg	James Earl Jones (born January 17, 1931) is an American actor. His career has spanned more than 60 years, and he has been described as \"one of America's most distinguished and versatile\" actors and \"one of the greatest actors in American history.\" Since his Broadway debut in 1957, Jones has won many awards, including a Tony Award and Golden Globe Award for his role in The Great White Hope. Jones has won three Emmy Awards, including two in the same year in 1991, and he also earned an Academy Award nomination for Best Actor in a Leading Role in the film version of The Great White Hope. He is also known for his voice roles as Darth Vader in the Star Wars film series and Mufasa in Disney's The Lion King as well as many other film, stage, and television roles.\r\n\r\nJones has been said to possess \"one of the best-known voices in show business, a stirring basso profundo that has lent gravel and gravitas to\" his projects, including live-action acting, voice acting, and commercial voice-overs.\r\n\r\nAs a child Jones had a stutter. In his episode of Biography, he said he overcame the affliction through poetry, public speaking, and acting, although it lasted for several years. A pre-med major in college, he went on to serve in the United States Army during the Korean War before pursuing a career in acting.\r\n\r\nOn November 12, 2011, he received an Honorary Academy Award. On November 9, 2015, Jones was honored to receive the Voice Arts Icon Awards.	0	2018-10-16 00:55:12.794697-04
d9019a6b-f84c-44d1-9bd7-e6ae1e750a06	Denzel Washington	https://i.imgur.com/f601H7X.jpg	Denzel Hayes Washington, Jr. (born December 28, 1954) is an American actor, filmmaker, director, and producer. He has received three Golden Globe awards, a Tony Award, and two Academy Awards: Best Supporting Actor for the historical war drama film Glory (1989) and Best Actor for his role as a corrupt cop in the crime thriller Training Day (2001).\r\n\r\nWashington has received much critical acclaim for his film work since the 1980s, including his portrayals of real-life figures such as South African anti-apartheid activist Steve Biko in Cry Freedom (1987), Muslim minister and human rights activist Malcolm X in Malcolm X (1992), boxer Rubin \"Hurricane\" Carter in The Hurricane (1999), football coach Herman Boone in Remember the Titans (2000), poet and educator Melvin B. Tolson in The Great Debaters (2007), and drug kingpin Frank Lucas in American Gangster (2007). He has been a featured actor in the films produced by Jerry Bruckheimer and has been a frequent collaborator of directors Spike Lee and the late Tony Scott. In 2016, Washington was selected as the recipient for the Cecil B. DeMille Lifetime Achievement Award at the 73rd Golden Globe Awards.\r\n\r\nIn 2002, Washington made his directorial debut with biographical film Antwone Fisher. His second directorial effort was The Great Debaters, released in 2007. He is currently in post production for his third directorial effort, Fences, starring himself opposite Viola Davis, slated for a December 25, 2016 release.	0	2018-10-16 00:55:12.794697-04
1fc483d5-cc50-407b-a053-6754ba2e1d97	Chris Pratt	https://i.imgur.com/JgDhBL7.jpg	Christopher Michael \"Chris\" Pratt (born June 21, 1979) is an American actor. He came to prominence from his television roles, including Bright Abbott in Everwood (2002–2006) and Andy Dwyer in the NBC sitcom Parks and Recreation (2009–2015).\r\n\r\nHis early film career began with supporting roles in such mainstream films as Wanted (2008), Moneyball (2011), The Five-Year Engagement (2012), Zero Dark Thirty (2012), Delivery Man (2013), and Her (2013) before achieving leading man status in 2014 after starring in two commercially successful films: The Lego Movie, a computer-animated adventure comedy; and Guardians of the Galaxy, a superhero film produced by Marvel Studios in which he portrayed Peter Quill / Star-Lord. In 2015, he starred in Jurassic World, the fourth installment in the Jurassic Park franchise and his most financially successful film. In 2015, Time Magazine named Pratt one of the 100 most influential people in the world on the annual Time 100 list.	0	2018-10-16 00:55:12.794697-04
96c888c9-cf1e-4ddc-92ea-bfdef3d5d0d7	Daisy Ridley	https://i.imgur.com/UcETFMW.jpg	Daisy Jazz Isobel Ridley (born 10 April 1992) is an English actress. She began her acting career by appearing in minor television roles and short films before being cast as the main protagonist, Rey, in the Star Wars sequel trilogy first appearing in Star Wars: The Force Awakens (2015).	0	2018-10-16 00:55:12.794697-04
54230d77-4594-4412-ac28-1186eb5578ad	Ethan Hawke	https://i.imgur.com/WY3y5Ab.jpg	Ethan Green Hawke (born November 6, 1970) is an American actor, writer and director. He has been nominated for four Academy Awards and a Tony Award. Hawke has directed two feature films, three Off-Broadway plays, and a documentary, and wrote the novels The Hottest State (1996), Ash Wednesday (2002), and Rules for a Knight (2015).\r\n\r\nHe made his film debut in 1985 with the science fiction feature Explorers, before making a breakthrough appearance in the 1989 drama Dead Poets Society. He then appeared in numerous films before taking a role in the 1994 Generation X drama Reality Bites, for which he received critical praise. In 1995, Hawke first appeared in Richard Linklater's romance trilogy, co-starring opposite Julie Delpy in Before Sunrise, and later in its sequels Before Sunset (2004) and Before Midnight (2013), all of which met with critical acclaim.\r\n\r\nHawke has been twice nominated for both the Academy Award for Best Adapted Screenplay and the Academy Award for Best Supporting Actor; his writing contributions to Before Sunset and Before Midnight were recognized, as were his performances in Training Day (2001) and Boyhood (2014). Hawke was further honored with SAG Award nominations for both films, along with BAFTA Award and Golden Globe Award nominations for the latter.\r\n\r\nHis other films include the science fiction drama Gattaca (1997), the contemporary adaptation of Hamlet (2000), the action thriller Assault on Precinct 13 (2005), the crime drama Before the Devil Knows You're Dead (2007), and the horror film Sinister (2012).	0	2018-10-16 00:55:12.794697-04
b929faa5-2b77-48d9-97e3-12f5c9f41732	Lee Byung-hun	https://i.imgur.com/oLxwXDD.jpg	Lee Byung-hun (born August 13, 1970) is a South Korean actor, singer and model. He has received critical acclaim for his work in a wide range of genres, most notably Joint Security Area (2000), A Bittersweet Life (2005), The Good, the Bad, the Weird (2008), the television series Iris (2009), I Saw the Devil (2010), and Masquerade (2012). Lee has starred in four films—Masquerade, Inside Men, Joint Security Area and The Good, the Bad, the Weird—on the list of highest-grossing films in South Korea.\r\n\r\nIn the United States, he is known for portraying Storm Shadow in G.I. Joe: The Rise of Cobra (2009) and its sequel G.I. Joe: Retaliation (2013), and starring alongside Bruce Willis in Red 2 (2013). He portrayed a T-1000 in Terminator Genisys (2015). Lee was the first South Korean actor to present an Oscar at the annual Academy Awards in Los Angeles and is a member of the Academy of Motion Picture Arts and Sciences. Lee, along with Ahn Sung-ki, were the first South Korean actors to imprint their hand and foot prints on the forecourt of Grauman's Chinese Theatre in Hollywood, Los Angeles.	0	2018-10-16 00:55:12.794697-04
f9cf7043-64b2-4846-aa05-b6e4b6875886	Sebastian Stan	https://i.imgur.com/lKT7u1f.jpg	Sebastian Stan (born August 13, 1982) is a Romanian-American actor, known for his role as James Buchanan \"Bucky\" Barnes / Winter Soldier in the Marvel Cinematic Universe. He also appeared in the 2010 film Black Swan. On television, Stan portrayed Carter Baizen on Gossip Girl, Prince Jack Benjamin on Kings, Jefferson on Once Upon a Time, and T.J. Hammond on the miniseries Political Animals. His role on Political Animals earned him a nomination for the Critics' Choice Television Award for Best Supporting Actor in a Movie/Miniseries. In 2015, he co-starred in Jonathan Demme's Ricki and the Flash, Ridley Scott's The Martian, and Bryan Buckley's The Bronze.	0	2018-10-16 00:55:12.794697-04
270153d8-4a4b-4462-a936-fdb1acf7304a	Robert Downey Jr.	https://i.imgur.com/1a7xOdt.jpg	Robert John Downey Jr. (born April 4, 1965) is an American actor. His career has included critical and popular success in his youth, followed by a period of substance abuse and legal troubles, and a resurgence of commercial success in middle age. For three consecutive years from 2012 to 2015, Downey has topped the Forbes list of Hollywood's highest-paid actors, making an estimated $80 million in earnings between June 2014 and June 2015.\r\n\r\nMaking his acting debut at the age of five, appearing in his father's film Pound (1970), Downey Jr. appeared in roles associated with the Brat Pack, such as the teen sci-fi comedy Weird Science (1985) and the drama Less Than Zero (1987). He starred as title character in the 1992 film Chaplin, which earned him a nomination for the Academy Award for Best Actor and won him the BAFTA Award for Best Actor in a Leading Role. After being released in 2000 from the California Substance Abuse Treatment Facility and State Prison where he was on drug charges, Downey joined the cast of the TV series Ally McBeal playing Calista Flockhart's love interest. This earned him a Golden Globe Award. His character was written out when Downey was fired after two drug arrests in late 2000 and early 2001. After his last stay in a court-ordered drug treatment program, Downey achieved sobriety.\r\n\r\nDowney Jr.'s career prospects improved when he featured in the mystery thriller Zodiac (2007), and the satirical action comedy Tropic Thunder (2008); for the latter he was nominated for an Academy Award for Best Supporting Actor. Beginning in 2008, Downey began portraying the role of Marvel Comics superhero Iron Man in the Marvel Cinematic Universe, appearing in several films as either the lead role, member of an ensemble cast, or in a cameo. Each of these films has grossed over $500 million at the box office worldwide; four of these—The Avengers, Avengers: Age of Ultron, Iron Man 3 and Captain America: Civil War—earned over $1 billion. Downey Jr. has also played the title character in Guy Ritchie's Sherlock Holmes (2009) and its sequel (2011).	0	2018-10-16 00:55:12.794697-04
8e14b32e-42f3-4fbf-80db-cc65c211679c	Scarlett Johansson	https://i.imgur.com/W1POt4y.jpg	Scarlett Johansson (born November 22, 1984) is an American actress, model, and singer. She made her film debut in North (1994). Johansson subsequently starred in Manny & Lo in 1996, and garnered further acclaim and prominence with roles in The Horse Whisperer (1998) and Ghost World (2001). She shifted to adult roles with her performances in Girl with a Pearl Earring (2003) and Sofia Coppola's Lost in Translation (2003), for which she won a BAFTA Award for Best Actress in a Leading Role.\r\n\r\nHer subsequent films included A Love Song for Bobby Long (2004), Woody Allen's Match Point (2005), The Island (2005), The Black Dahlia (2006), The Prestige (2006), The Other Boleyn Girl (2008), Vicky Cristina Barcelona (2008), He's Just Not That Into You (2009), Don Jon (2013), Her (2013), Under the Skin (2013), and Lucy (2014). Since 2010, Johansson has also portrayed the Marvel Comics character Black Widow in the Marvel Cinematic Universe. She won the Tony Award for Best Performance by a Featured Actress in a Play for her performance in the 2010 Broadway revival of Arthur Miller's A View from the Bridge. As a singer, Johansson has released two albums, Anywhere I Lay My Head and Break Up.\r\n\r\nJohansson is considered one of Hollywood's modern sex symbols, and has frequently appeared in published lists of the sexiest women in the world. As of July 2016, she is the highest-grossing actress of all time in North America, and tenth overall, with her films making over $3.3 billion.	0	2018-10-16 00:55:12.794697-04
d4d75d21-2a04-4a0d-b98c-bb010db27fd4	Jeremy Renner	https://i.imgur.com/AdxFHGf.jpg	Jeremy Lee Renner (born January 7, 1971) is an American actor and singer. Throughout the 2000s, Renner appeared largely in independent films such as Dahmer (2002) and Neo Ned (2005). He also appeared in supporting roles in bigger films such as S.W.A.T. (2003) and 28 Weeks Later (2007). He then turned in a much-praised performance in The Town (2010), for which he received an Academy Award nomination for Best Supporting Actor.\r\n\r\nRenner is best known for his roles in The Hurt Locker (2008) for which he received an Academy Award nomination for Best Actor, and for playing Hawkeye in the Marvel Cinematic Universe films Thor (2011), Marvel's The Avengers (2012), Avengers: Age of Ultron (2015) and Captain America: Civil War (2016). He also appeared in commercially successful films such as Mission: Impossible – Ghost Protocol (2011), The Bourne Legacy (2012), Hansel and Gretel: Witch Hunters (2013), American Hustle (2013), and Mission: Impossible - Rogue Nation (2015).	0	2018-10-16 00:55:12.794697-04
80331bf3-89af-4dfc-85c8-e88ad19b4e98	Vincent D'Onofrio	https://i.imgur.com/iy08bbq.jpg	Vincent Philip D'Onofrio (born June 30, 1959) is an American actor, producer, and singer.\r\n\r\nHe is known for his roles as Private Leonard Lawrence (\"Gomer Pyle\") in Stanley Kubrick's war film Full Metal Jacket (1987), Wilson Fisk in the Netflix series Daredevil, Det. Robert Goren in Law & Order: Criminal Intent, Edgar/the Bug in Men in Black (1997), and Vic Hoskins in Jurassic World (2015). Among other honors, D'Onofrio is a Saturn Award winner and an Emmy Award nominee.	0	2018-10-16 00:55:12.794697-04
c4926dde-da85-4b8c-a522-9f4c5e5b68ff	Oscar Isaac	https://i.imgur.com/FVDblYH.jpg	Oscar Isaac (born March 9, 1979) is a Guatemalan-American actor and musician. He is known for his lead film roles in the comedy-drama Inside Llewyn Davis (2013), for which he received a Golden Globe Award nomination, the crime drama A Most Violent Year (2014) and the science fiction thriller Ex Machina (2015). In 2006, he portrayed Saint Joseph, husband of Mary, in The Nativity Story. He also portrayed José Ramos-Horta, former president of East Timor, in the Australian film Balibo for which he won the AACTA Award for Best Actor in a Supporting Role. His ethnically ambiguous appearance has allowed him to portray characters of many different nationalities and ethnicities, such as mixed European, Egyptian, Polish, English, French, Mexican, East Timorese, Welsh, Indonesian, Greek, Cuban, Israeli and Armenian.\r\n\r\nHe appeared in Star Wars: The Force Awakens (2015), as X-wing pilot Poe Dameron, and in X-Men: Apocalypse (2016), as the titular mutant supervillain Apocalypse. He also headlined the HBO miniseries Show Me a Hero, as politician Nick Wasicsko in 2015, which earned him the Golden Globe Award for Best Actor – Miniseries or Television Film. In 2016, Time named Isaac one of the 100 most influential people in the world on the annual Time 100 list.	0	2018-10-16 00:55:12.794697-04
1a4599b9-799c-4745-8aff-92bff21f4df9	Adam Driver	https://i.imgur.com/tenvO0M.jpg	Adam Douglas Driver (born November 19, 1983) is an American actor. He made his Broadway debut in Mrs. Warren's Profession (2010). He returned to Broadway in the 2011 production of Man and Boy and made his feature film debut in J. Edgar (2011). Driver appeared in supporting roles in a wide range of films, including Lincoln (2012), Frances Ha (2012) and Inside Llewyn Davis (2013). In 2014, Driver starred in While We're Young and won the Volpi Cup for his role in Hungry Hearts (2014).\r\n\r\nDriver gained worldwide attention and acclaim for playing the villain Kylo Ren in Star Wars: The Force Awakens (2015), a role which he is set to reprise in the future Star Wars films. For his supporting part as Adam Sackler in the HBO comedy-drama series Girls, Driver has received three consecutive nominations for Primetime Emmy Award for Outstanding Supporting Actor in a Comedy Series.	0	2018-10-16 00:55:12.794697-04
10f93de5-d4bd-48d0-af94-f41f6189fe0a	Mark Hamill	https://i.imgur.com/S3iOaah.jpg	Mark Richard Hamill (born September 25, 1951) is an American actor, voice actor and writer best known for his portrayal of Luke Skywalker in the Star Wars film series. Hamill also starred in the films Corvette Summer (1978) and The Big Red One (1980), among other television shows and movies. He has also appeared on stage in several theater productions, primarily during the 1980s.\r\n\r\nHamill is a prolific voice actor who has voiced characters in many animated television shows, movies, and video games since the 1970s. He is best known for his long-standing role as DC Comics' the Joker, commencing with Batman: The Animated Series in 1992.	0	2018-10-16 00:55:12.794697-04
26981d94-f045-4d3f-9eec-3ff2e0f82922	John Boyega	https://i.imgur.com/XfaD3lo.jpg	John Adedayo Adegboyega (born 17 March 1992), known professionally as John Boyega, is a British actor and producer best known for playing Finn in the 2015 film Star Wars: The Force Awakens, the seventh film of the Star Wars series. Boyega rose to prominence in his native United Kingdom for his role as Moses in the 2011 sci-fi comedy film Attack the Block.\r\n\r\nOther credits include historical fiction drama film Half of a Yellow Sun (2013), four episodes of the television series 24: Live Another Day and the drama Imperial Dreams (2014).	0	2018-10-16 00:55:12.794697-04
f48c7da2-2e0c-4a7e-aeaa-3b85c9853011	Shailene Woodley	https://i.imgur.com/3p55zsC.jpg	Shailene Diann Woodley (born November 15, 1991) is an American actress. She first received attention as Amy on the ABC Family television series Secret Life of the American Teenager (2008–13) and rose to stardom in various popular and critically acclaimed films such as The Descendants (2011), The Spectacular Now (2013), The Fault in Our Stars (2014), The Divergent Series (2014–) as Beatrice \"Tris\" Prior and Snowden (2016). For her work, she has garnered a Cannes Trophée Chopard, Sundance Film Festival Special Jury Prize for Dramatic Acting, Critics Choice Award, People's Choice Award, Independent Spirit Award and was nominated for a Screen Actors Guild Award, BAFTA Award and a Golden Globe Award for Best Supporting Actress in a Motion Picture.	0	2018-10-16 00:55:12.794697-04
2ceec419-f3f7-4ced-8496-2c6522305f7c	Zachary Quinto	https://i.imgur.com/KR2x8BO.jpg	Zachary John Quinto (born June 2, 1977) is an American actor and film producer. He is best known for his roles as Sylar on the science fiction drama series Heroes (2006–2010), Spock in the reboot Star Trek (2009) and its sequels Star Trek Into Darkness (2013) and Star Trek Beyond (2016), as well as his Emmy nominated performance in American Horror Story: Asylum. He also appeared in smaller roles on television series such as So NoTORIous, The Slap, and 24.	0	2018-10-16 00:55:12.794697-04
f0094793-6c9d-4750-9ab0-a52de7862484	Nicolas Cage	https://i.imgur.com/2Qkwe2i.jpg	Nicolas Kim Coppola (born January 7, 1964), known professionally as Nicolas Cage, is an American actor and producer. He has performed in leading roles in a variety of films, ranging from romantic comedies and dramas to science fiction and action films. In the early years of his career, Cage starred in films such as Valley Girl (1983), Racing with the Moon (1984), Birdy (1984), Peggy Sue Got Married (1986), Raising Arizona (1987), Moonstruck (1987), Vampire's Kiss (1989), Wild at Heart (1990), Honeymoon in Vegas (1992), and Red Rock West (1993).\r\n\r\nCage received an Academy Award, a Golden Globe, and Screen Actors Guild Award for his performance as an alcoholic Hollywood writer in Leaving Las Vegas (1995) before coming to the attention of wider audiences with mainstream films, such as The Rock (1996), Face/Off (1997), Con Air (1997) and City of Angels (1998). He earned his second Academy Award nomination for his performance as Charlie and Donald Kaufman in Adaptation (2002). In 2002, he directed the film Sonny, for which he was nominated for Grand Special Prize at Deauville Film Festival. Cage owns the production company Saturn Films and has produced films such as Shadow of the Vampire (2000) and The Life of David Gale (2003).\r\n\r\nHe has also appeared in National Treasure (2004), Lord of War (2005), Bad Lieutenant: Port of Call New Orleans (2009), and Kick-Ass (2010). Films such as Ghost Rider (2007) and Knowing (2009) were box office successes. Cage has been strongly criticized in recent years for his choice of roles, some of which have been universally panned. However, he recently starred in The Croods and Joe, both of which were warmly received by critics.	0	2018-10-16 00:55:12.794697-04
78377bd7-4ce8-43f4-befb-a5ddd726b7b5	Lee J. Cobb	https://i.imgur.com/Qe34stE.jpg	Lee J. Cobb (December 8, 1911 - February 11, 1976) was an American actor. He is best known for his performances in 12 Angry Men (1957), On the Waterfront (1954), and The Exorcist (1973). He also played the role of Willy Loman in the original Broadway production of Arthur Miller's 1949 play Death of a Salesman under the direction of Elia Kazan. On television, Cobb costarred in the first four seasons of the Western series The Virginian. He typically played arrogant, intimidating, and abrasive characters, but often had roles as respectable figures such as judges and police officers.	0	2018-10-16 00:55:12.794697-04
23b7eb21-38f4-4ac2-8cfc-5f50d2a391c3	Melissa Leo	https://i.imgur.com/ksbTmti.jpg	Melissa Chessington Leo (born September 14, 1960) is an American actress. After appearing on several television shows and films in the 1980s, her breakthrough role came in 1993 as Det. Sgt. Kay Howard on the television series Homicide: Life on the Street for the show's first five seasons (1993–97). She had also previously been a regular on the television shows All My Children and The Young Riders.\r\n\r\nLeo received critical acclaim for her performance as Ray Eddy in the film Frozen River (2008), earning several nominations and awards, including an Academy Award nomination for Best Actress. In 2010, Leo won several awards for her performance as Alice Eklund-Ward in the film The Fighter, including the Golden Globe, SAG, and Academy Award for Best Supporting Actress.\r\n\r\nIn 1985 she was nominated for a Daytime Emmy Award for her performance on All My Children. In 2013 she won the Emmy Award for her guest-role on the television series Louie. She recently starred in the 2015 Fox event series Wayward Pines as Nurse Pam.	0	2018-10-16 00:55:12.794697-04
eeb8d4f7-34fb-4959-8ed0-fb752922bd2e	Tommy Lee Jones	https://i.imgur.com/bFSQgzO.jpg	Tommy Lee Jones (born September 15, 1946) is an American actor and filmmaker. He has received four Academy Award nominations, winning Best Supporting Actor for his performance as U.S. Marshal Samuel Gerard in the 1993 thriller film The Fugitive.\r\n\r\nHis other notable starring roles include former Texas Ranger Woodrow F. Call in the TV mini-series Lonesome Dove, Agent K in the Men in Black film series, Sheriff Ed Tom Bell in No Country for Old Men, the villain Two-Face in Batman Forever, terrorist William Strannix in Under Siege, a Texas Ranger in Man of the House, rancher Pete Perkins in The Three Burials of Melquiades Estrada, which he directed, Colonel Chester Phillips in Captain America: The First Avenger and Warden Dwight McClusky in Natural Born Killers. Jones has also portrayed real-life figures such as businessman Howard Hughes, Radical Republican Congressman Thaddeus Stevens, executed murderer Gary Gilmore, U.S. Army General Douglas MacArthur, Oliver Lynn, husband of Loretta Lynn in Coal Miner's Daughter, and baseball great Ty Cobb.	0	2018-10-16 00:55:12.794697-04
23bd9ad3-1c6a-4321-b2fb-441b737e391a	Linda Fiorentino	https://i.imgur.com/MS0r2ML.jpg	Linda Fiorentino (born March 9, 1959 or 1960) is an American actress. She became known for her leading role in the 1985 coming of age drama film Vision Quest; then, in the same year she earned wide recognition for her role in the action film Gotcha! (1985); later on, she appeared in After Hours (1985), Queens Logic (1991) and Jade (1995).\r\n\r\nIn 1997, Fiorentino´s career took an upturn due to her role in the science fiction action comedy film Men in Black; afterwards she appeared in the films Dogma (1999), Where the Money Is (2000) and Liberty Stands Still (2002). For her performance in the 1994 film The Last Seduction, she won the New York Film Critics Circle Award for Best Actress, the London Film Critics' Circle Award for Actress of the Year, and was nominated for the BAFTA Award for Best Actress in a Leading Role.	0	2018-10-16 00:55:12.794697-04
1cadcc67-67e2-468e-8ea5-19fdb211d56d	Rip Torn	https://i.imgur.com/iM11WQz.png	Elmore Rual Torn Jr. (born February 6, 1931), known within his family and professionally as Rip Torn, is an American actor, voice artist, and comedian.\r\n\r\nTorn was nominated for the Academy Award for Best Supporting Actor for his part as Marsh Turner in Cross Creek (1983). His work includes the role of Artie the producer on The Larry Sanders Show, for which he was nominated for six Emmy Awards, winning in 1996. Torn also won an American Comedy Award for Funniest Supporting Male in a Series, and two CableACE Awards for his work on the show, and was nominated for a Satellite Award in 1997 as well.	0	2018-10-16 00:55:12.794697-04
54b25739-4f94-44ef-a1cf-06dcb144d2c8	Morgan Freeman	https://i.imgur.com/qVf9umF.jpg	Morgan Freeman (born June 1, 1937) is an American actor, producer and narrator. Freeman won an Academy Award in 2005 for Best Supporting Actor with Million Dollar Baby (2004), and he has received Oscar nominations for his performances in Street Smart (1987), Driving Miss Daisy (1989), The Shawshank Redemption (1994) and Invictus (2009). He has also won a Golden Globe Award and a Screen Actors Guild Award.\r\n\r\nFreeman has appeared in many other box office hits, including Glory (1989), Robin Hood: Prince of Thieves (1991), Seven (1995), Deep Impact (1998), The Sum of All Fears (2002), Bruce Almighty (2003), The Dark Knight Trilogy (2005–2012), The Lego Movie (2014), and Lucy (2014). He is known for his distinctively smooth, deep voice and his skill at narration. He got his break as part of the cast of the 1970s children's program The Electric Company. Morgan Freeman is ranked as the 3rd highest box office star with over $4.316 billion total box office gross, an average of $74.4 million per film.	0	2018-10-16 00:55:12.794697-04
0d00f5af-0415-4c76-b594-5a9ef9ffb637	Kevin Spacey	https://i.imgur.com/6oLcbmK.jpg	Kevin Spacey Fowler (born July 26, 1959) is an American actor of screen and stage, film director, producer, screenwriter and singer who has resided in the United Kingdom since 2003. He began his career as a stage actor during the 1980s before obtaining supporting roles in film and television. He gained critical acclaim in the early 1990s that culminated in his first Academy Award for Best Supporting Actor for the neo-noir crime thriller The Usual Suspects (1995), and an Academy Award for Best Actor for midlife crisis-themed drama American Beauty (1999).\r\n\r\nHis other starring roles have included the comedy-drama film Swimming with Sharks (1994), psychological thriller Seven (1995), the neo-noir crime film L.A. Confidential (1997), the drama Pay It Forward (2000), the science fiction-mystery film K-PAX (2001), and the role of Lex Luthor in the superhero film Superman Returns (2006).\r\n\r\nIn Broadway theatre, Spacey won a Tony Award for his role in Lost in Yonkers. He was the artistic director of the Old Vic theatre in London from 2004 until stepping down in mid-2015. Since 2013, Spacey has played Frank Underwood in the Netflix political drama series House of Cards. For his role as Underwood, he has won a Golden Globe Award for Best Actor Television Series Drama and two consecutive Screen Actors Guild Award for Outstanding Performance by a Male Actor in a Drama Series.	0	2018-10-16 00:55:12.794697-04
164ae33d-5b15-46dd-9e4a-064b5087c545	Martin Balsam	https://i.imgur.com/2xpvBnf.jpg	Martin Henry Balsam (November 4, 1919 - February 13, 1996) was an American actor. He is best known for a number of renowned film roles, including detective Milton Arbogast in Alfred Hitchcock's Psycho (1960), Arnold Burns in A Thousand Clowns (1965) (for which he won the Academy Award for Best Supporting Actor), Juror #1 in 12 Angry Men (1957), and Mr. Green in The Taking of Pelham One Two Three (1974), as well as for his role as Murray Klein in the television sitcom Archie Bunker's Place (1979 - 1983).	0	2018-10-16 00:55:12.794697-04
ec89ae36-1f13-4405-a664-dfa69a662096	Jack Warden	https://i.imgur.com/5J4KHlI.jpg	Jack Warden (September 18, 1920 - July 19, 2006) was an American character actor of film and television. He was twice nominated for an Academy Award for Best Supporting Actor, for Shampoo (1975) and Heaven Can Wait (1978). He received a BAFTA nomination for the former movie and won an Emmy for his performance in Brian's Song (1971).	0	2018-10-16 00:55:12.794697-04
a6d16ef3-3872-4e27-bdbe-b06c48800e97	Gwyneth Paltrow	https://i.imgur.com/d1FvIxc.jpg	Gwyneth Kate Paltrow (born September 27, 1972) is an American actress, singer, and food writer. She gained early notice for her work in films such as the thriller Seven (1995) and the period drama Emma (1996). Following starring roles in the romantic comedy-drama Sliding Doors (1998) and the thriller A Perfect Murder (1998), Paltrow garnered worldwide recognition through her performance in Shakespeare in Love (1998), for which she won an Academy Award, a Golden Globe Award and two Screen Actors Guild Awards. Paltrow has portrayed supporting, as well as lead roles, in films such as The Talented Mr. Ripley (1999), The Royal Tenenbaums (2001), Shallow Hal (2001), and Proof (2005), for which she earned a Golden Globe nomination.\r\n\r\nSince 2008, Paltrow has portrayed Pepper Potts, the female lead of the Iron Man franchise in the Marvel Cinematic Universe, in Iron Man (2008), Iron Man 2 (2010), The Avengers (2012), and Iron Man 3 (2013). She won an Emmy Award for Outstanding Guest Actress in a Comedy Series in 2011 for her role as Holly Holliday on the FOX hit television series Glee, her first and so far only foray into television acting, for the episode The Substitute. She reprised this role four more times throughout the show's run.\r\n\r\nFollowing relationships with Brad Pitt and Ben Affleck, Paltrow married Chris Martin, the lead vocalist of band Coldplay, in 2003; they have two children. They announced their separation in March 2014 and divorced in July 2016. Paltrow has been dating Glee and American Horror Story co-creator Brad Falchuk since August 2014.\r\n\r\nPaltrow has been the face of Estée Lauder's Pleasures perfume since 2005. She is also the face of American fashion brand Coach, owner of a lifestyle company, and author of two cookbooks.	0	2018-10-16 00:55:12.794697-04
f5ba9e8a-d4cb-4de7-9baa-4234ab7b77f8	John C. McGinley	https://i.imgur.com/Mi9DTFj.jpg	John Christopher McGinley (born August 3, 1959) is an American actor, author and former comedian. He is most notable for his roles as Perry Cox in Scrubs, Bob Slydell in Office Space, Captain Hendrix in The Rock, Sergeant Red O'Neill in Oliver Stone's Platoon and Marv in Stone's Wall Street. He has also written and produced for television and film. Apart from acting, McGinley is also an author, a board member and international spokesperson for the Global Down Syndrome Foundation, and a spokesperson for the National Down Syndrome Society.	0	2018-10-16 00:55:12.794697-04
1cb2533f-ff15-4e7d-a3e4-652445c95e75	Harrison Ford	https://i.imgur.com/0fCjAhr.jpg	Harrison Ford (born July 13, 1942) is an American actor and film producer. He gained worldwide fame for his starring roles as Han Solo in the original Star Wars epic space opera trilogy, and as the title character of the Indiana Jones film series. Ford is also known for his roles as Rick Deckard in the neo-noir dystopian science fiction film Blade Runner (1982), John Book in the thriller Witness (1985), for which he was nominated for the Academy Award for Best Actor, and Jack Ryan in the action films Patriot Games (1992) and Clear and Present Danger (1994). Most recently, Ford reprised his role of Han Solo in Star Wars: The Force Awakens (2015) and will next reprise his role as Deckard in Blade Runner 2049 (2017).\r\n\r\nHis career has spanned six decades and includes roles in several Hollywood blockbusters; including the epic war film Apocalypse Now (1979), the legal drama Presumed Innocent (1990), the action film The Fugitive (1993), the political action thriller Air Force One (1997) and the psychological thriller What Lies Beneath (2000). Six of his films have been inducted into the National Film Registry: American Graffiti (1973), The Conversation (1974), Star Wars (1977), The Empire Strikes Back (1980), Raiders of the Lost Ark (1981) and Blade Runner.\r\n\r\nIn 1997, Ford was ranked No. 1 in Empire's \"The Top 100 Movie Stars of All Time\" list.[citation needed] As of 2016, the U.S. domestic box-office grosses of Ford's films total over US$4.7 billion, with worldwide grosses surpassing $6 billion, making Ford the highest-grossing U.S. domestic box-office star. Ford is married to actress Calista Flockhart, who is known for playing the title role in the comedy-drama series Ally McBeal.	0	2018-10-16 00:55:12.794697-04
157f1600-b8cc-4222-82da-d01908255516	Tim Robbins	https://i.imgur.com/2ZnOLGW.jpg	Timothy Francis \"Tim\" Robbins (born October 16, 1958) is an American actor, screenwriter, director, producer, activist and musician. He is well known for his portrayal of Andy Dufresne in the prison drama film The Shawshank Redemption (1994). His other roles include Nuke LaLoosh in Bull Durham, Jacob Singer in Jacob's Ladder, Griffin Mill in The Player, and Dave Boyle in Mystic River, for which he won an Academy Award for Best Supporting Actor, and for directing films such as Dead Man Walking.	0	2018-10-16 00:55:12.794697-04
6671f1b7-ecef-426a-aa0c-266a0f187706	Bob Gunton	https://i.imgur.com/QY3kFry.jpg	Robert Patrick \"Bob\" Gunton, Jr. (born November 15, 1945) is an American actor. He is known for playing strict, authoritarian characters, with his best known roles as Warden Samuel Norton in the 1994 prison film The Shawshank Redemption, Chief George Earle in 1993's Demolition Man, Dr. Walcott, the domineering dean of Virginia Medical School in Patch Adams, and President Juan Peron in the original Broadway production of Evita, for which he received a Tony Award nomination. He also appears in the Daredevil TV series as Leland Owlsley.	0	2018-10-16 00:55:12.794697-04
a7de905e-6b6d-48c1-a570-0dc4d4d2264d	Clancy Brown	https://i.imgur.com/hawKFoG.jpg	Clarence J. \"Clancy\" Brown III (born January 5, 1959) is an American actor and voice actor known for his roles as The Kurgan in the fantasy film Highlander, Captain Byron Hadley in prison drama film The Shawshank Redemption and Brother Justin Crowe in the television series Carnivàle. Brown has also provided his voice to many films, television series, and video games, including Lex Luthor in various media, Doctor Neo Cortex in the Crash Bandicoot video game series, and Mr. Krabs in the animated series SpongeBob SquarePants.	0	2018-10-16 00:55:12.794697-04
30a3290b-8975-4567-97af-b6073a642883	William Sadler	https://i.imgur.com/xE9voRx.jpg	William Thomas Sadler (born April 13, 1950) is an American film and television actor. His television and motion picture roles have included Lewis Burwell \"Chesty\" Puller in The Pacific, Luther Sloan in Star Trek: Deep Space Nine, Sheriff Jim Valenti in Roswell, convict Heywood in The Shawshank Redemption, Senator Vernon Trent in Hard To Kill, and the Grim Reaper in Bill & Ted's Bogus Journey, a role for which he won the Saturn Award for Best Supporting Actor, and his role as the villainous Colonel Stuart opposite Bruce Willis in Die Hard 2. He played the character of President of the United States, Matthew Ellis, in Iron Man 3 and in Marvel's Agents of S.H.I.E.L.D., and recurs as Steve McGarrett's murdered father in the latter-day 2000's remake of Hawaii Five-O. In 2015, Sadler had an appearance in the TV series Z Nation.	0	2018-10-16 00:55:12.794697-04
3408a4bc-56ee-470e-b002-c735ddd7d5d2	Al Pacino	https://i.imgur.com/7RyxS6Z.jpg	Alfredo James \"Al\" Pacino (born April 25, 1940) is an American actor of stage and screen, filmmaker, and screenwriter. Pacino has had a career spanning more than fifty years, during which time he has received numerous accolades and honors both competitive and honorary, among them an Academy Award, two Tony Awards, two Primetime Emmy Awards, a British Academy Film Award, four Golden Globe Awards, the Lifetime Achievement Award from the American Film Institute, the Golden Globe Cecil B. DeMille Award, and the National Medal of Arts. He is also one of few performers to have won a competitive Oscar, an Emmy and a Tony Award for acting, dubbed the \"Triple Crown of Acting\".\r\n\r\nA method actor and former student of the HB Studio and the Actors Studio in New York City, where he was taught by Charlie Laughton and Lee Strasberg, Pacino made his feature film debut with a minor role in Me, Natalie (1969) and gained favorable notices for his lead role as a heroin addict in The Panic in Needle Park (1971). He achieved international acclaim and recognition for his breakthrough role as Michael Corleone in Francis Ford Coppola's The Godfather (1972). He received his first Oscar nomination and would reprise the role in the equally successful sequels The Godfather Part II (1974) and The Godfather Part III (1990). Pacino's performance as Corleone is now regarded as one of the greatest screen performances in film history.\r\n\r\nPacino received his first Best Actor Oscar nomination for Serpico (1973); he was also nominated for The Godfather Part II, Dog Day Afternoon (1975) and ...And Justice for All (1979) and won the award in 1993 for his performance as a blind Lieutenant Colonel in Scent of a Woman (1992). For his performances in The Godfather, Dick Tracy (1990) and Glengarry Glen Ross (1992), Pacino was nominated for the Academy Award for Best Supporting Actor. Other notable roles include Tony Montana in Scarface (1983), Carlito Brigante in Carlito's Way (1993), Lieutenant Vincent Hanna in Heat (1995), Benjamin Ruggiero in Donnie Brasco (1997), Lowell Bergman in The Insider (1999) and Detective Will Dormer in Insomnia (2002). In television, Pacino has acted in several productions for HBO including the miniseries Angels in America (2003) and the Jack Kevorkian biopic You Don't Know Jack (2010), both of which won him the Primetime Emmy Award for Outstanding Lead Actor in a Miniseries or a Movie.\r\n\r\nIn addition to his work in film, Pacino has had an extensive career on stage and is a two-time Tony Award winner, in 1969 and 1977, for his performances in Does a Tiger Wear a Necktie? and The Basic Training of Pavlo Hummel respectively. A lifelong fan of Shakespeare, Pacino directed and starred in Looking for Richard (1996), a documentary film about the play Richard III, a role which Pacino had earlier portrayed on-stage in 1977. He has also acted as Shylock in a 2004 feature film adaptation and a 2010 production of The Merchant of Venice. Having made his filmmaking debut with Looking for Richard, Pacino has also directed and starred in the independent film Chinese Coffee (2000) and the films Wilde Salomé (2011) and Salomé (2013), about the play Salomé by Oscar Wilde. Since 1994, Pacino has been the joint president of the Actors Studio with Ellen Burstyn and Harvey Keitel.	0	2018-10-16 00:55:12.794697-04
7566a5f2-45c6-4019-8e97-6894ea51156f	Marlon Brando	https://i.imgur.com/MUHgqZ8.jpg	Marlon Brando, Jr. (April 3, 1924 - July 1, 2004) was an American actor, film director and activist. He is credited with bringing a realism to film acting, and is considered one of the greatest and most influential actors of all time. He helped to popularize the Stanislavski system of acting, studying with Stella Adler in the 1940s. Brando is most famous for his Academy Award-winning performances as Terry Malloy in On the Waterfront (1954) and Vito Corleone in The Godfather (1972), as well as performances in A Streetcar Named Desire (1951), Viva Zapata! (1952), Julius Caesar (1953), The Wild One (1953), Reflections in a Golden Eye (1967), Last Tango in Paris (1972), and Apocalypse Now (1979). Brando was also an activist for many causes, notably the African-American Civil Rights Movement and various Native American movements.\r\n\r\nHe initially gained acclaim and an Academy Award nomination for reprising the role of Stanley Kowalski in the 1951 film adaptation of Tennessee Williams' play A Streetcar Named Desire, a role that he had originated successfully on Broadway. He received further praise for his performance as Terry Malloy in On the Waterfront, and his portrayal of the rebel motorcycle gang leader Johnny Strabler in The Wild One proved to be a lasting image in popular culture. Brando received Academy Award nominations for playing Emiliano Zapata in Viva Zapata!; Mark Antony in Joseph L. Mankiewicz's 1953 film adaptation of Shakespeare's Julius Caesar; and Air Force Major Lloyd Gruver in Sayonara (1957), an adaption of James Michener's 1954 novel. Brando was included in a list of Top Ten Money Making Stars three times in the 1950s, coming in at number 10 in 1954, number 6 in 1955, and number 4 in 1958.\r\n\r\nThe 1960s proved to be a fallow decade for Brando. He directed and starred in the cult western film One-Eyed Jacks, a critical and commercial flop, after which he delivered a series of box-office failures, beginning with the 1962 film adaptation of the novel Mutiny on the Bounty. After 10 years, during which he did not appear in a successful film, he won his second Academy Award for playing Vito Corleone in Francis Ford Coppola's The Godfather, a role critics consider among his greatest. The Godfather was then one of the most commercially successful films of all time. With that and his Oscar-nominated performance in Last Tango in Paris, Brando re-established himself in the ranks of top box-office stars, placing sixth and tenth in the Money Making Stars poll in 1972 and 1973, respectively. Brando took a four-year hiatus before appearing in The Missouri Breaks (1976). After this, he was content with being a highly paid character actor in cameo roles, such as in Superman (1978) and The Formula (1980), before taking a nine-year break from motion pictures. According to the Guinness Book of World Records, Brando was paid a record $3.7 million ($14 million in inflation-adjusted dollars) and 11.75% of the gross profits for 13 days' work on Superman. He finished out the 1970s with his controversial performance as Colonel Kurtz in another Coppola film, Apocalypse Now, a box-office hit for which he was highly paid and which helped finance his career layoff during the 1980s.\r\n\r\nBrando was ranked by the American Film Institute as the fourth-greatest movie star among male movie stars whose screen debuts occurred in or before 1950. He was one of only three professional actors, along with Charlie Chaplin and Marilyn Monroe, named in 1999 by Time magazine as one of its 100 Most Important People of the Century. He died of respiratory failure on July 1, 2004, at age 80.	0	2018-10-16 00:55:12.794697-04
2f635a08-126a-4b0f-ade4-839ac788aab3	E.G. Marshall	https://i.imgur.com/Ov4NReH.jpg	E. G. Marshall (June 18, 1914 - August 24, 1998) was an American actor, best known for his television roles as the lawyer Lawrence Preston on The Defenders in the 1960s and as neurosurgeon David Craig on The Bold Ones: The New Doctors in the 1970s. Among his film roles he is perhaps best known as the unflappable, conscientious \"Juror #4\" in Sidney Lumet's courtroom drama 12 Angry Men (1957). He also played the President of the United States in Superman II (1980) and Superman II: The Richard Donner Cut (2006).	0	2018-10-16 00:55:12.794697-04
47550a92-cb2e-4246-82a7-07838e516c60	Robert Duvall	https://i.imgur.com/XzPUTYv.jpg	Robert Selden Duvall (born January 5, 1931) is an American actor and filmmaker. He has been nominated for seven Academy Awards (winning for his performance in Tender Mercies), seven Golden Globes (winning four), and has multiple nominations and one win each of the BAFTA, Screen Actors Guild Award, and Emmy Award. He received the National Medal of Arts in 2005. He has starred in some of the most acclaimed and popular films and television series of all time, including To Kill a Mockingbird (1962), The Twilight Zone (1963), The Outer Limits (1964), Bullitt (1968), True Grit (1969), MASH (1970), THX 1138 (1971), Joe Kidd (1972), The Godfather (1972), The Godfather Part II (1974), The Conversation (1974), Network (1976), Apocalypse Now (1979), The Handmaid's Tale (1990), Rambling Rose (1991), and Falling Down (1993).\r\n\r\nHe began appearing in theatre during the late 1950s, moving into television and film roles during the early 1960s, playing Boo Radley in To Kill a Mockingbird (1962) and appearing in Captain Newman, M.D. (1963). He landed many of his most famous roles during the early 1970s, such as Major Frank Burns in the blockbuster comedy MASH (1970) and the lead role in THX 1138 (1971), as well as Horton Foote's adaptation of William Faulkner's Tomorrow (1972), which was developed at The Actors Studio and is Duvall's personal favorite. This was followed by a series of critically lauded performances in commercially successful films.\r\n\r\nSince then, Duvall has continued to act in both film and television with such productions as Tender Mercies (1983), The Natural (1984), Colors (1988), the television mini-series Lonesome Dove (1989), Stalin (1992), The Man Who Captured Eichmann (1996), A Family Thing (1996), The Apostle (1997), A Civil Action (1998), Gods and Generals (2003), Secondhand Lions (2003), Broken Trail (2006), Get Low (2010), Jack Reacher (2012), and The Judge (2014).	0	2018-10-16 00:55:12.794697-04
9ef521a3-757f-464e-8780-18dc2c2fdabc	James Caan	https://i.imgur.com/UFeREju.jpg	James Edmund Caan (born March 26, 1940) is an American actor. After early roles in The Glory Guys (1965) and El Dorado (1966), he came to prominence in the 1970s with significant roles in films such as Brian's Song (1971), The Gambler (1974), Funny Lady (1975), and A Bridge Too Far (1977). For his signature role in The Godfather (1972), that of hot-tempered Sonny Corleone, Caan was nominated for the Academy Award for Best Supporting Actor and the corresponding Golden Globe.\r\n\r\nCaan's subsequent notable performances include roles in Thief (1981), Misery (1990), For the Boys (1991), Bottle Rocket (1996) and Elf (2003), as well as the role of \"Big Ed\" Deline in the television series Las Vegas (2003-08). He also prominently lent his voice to Cloudy with a Chance of Meatballs (2009) and Cloudy with a Chance of Meatballs 2 (2013) as Tim Lockwood, father of Bill Hader's protagonist Flint Lockwood.	0	2018-10-16 00:55:12.794697-04
19e9f98a-f3c7-4d0d-b4c1-90d51ccfc6cc	Talia Shire	https://i.imgur.com/vPTrL5X.jpg	Talia Rose Shire (born April 25, 1946) is an Italian-American actress most known for her roles as Connie Corleone in The Godfather films and Adrian Balboa in the Rocky series. For her work in The Godfather Part II and Rocky, Shire has been nominated for two Academy Awards for Best Supporting Actress and Best Actress, respectively.	0	2018-10-16 00:55:12.794697-04
2fa869ad-358b-4b69-8d3d-5db38592f0de	Robert De Niro	https://i.imgur.com/Ljpaahk.jpg	Robert Anthony De Niro (born August 17, 1943) is an American actor and producer who has both Italian and American citizenship. He was cast as the young Vito Corleone in the 1974 film The Godfather Part II, for which he won the Academy Award for Best Supporting Actor. His longtime collaboration with director Martin Scorsese earned him the Academy Award for Best Actor for his portrayal of Jake La Motta in the 1980 film Raging Bull. He received the AFI Life Achievement Award in 2003, the Golden Globe Cecil B. DeMille Award in 2010, and the Presidential Medal of Freedom from President Barack Obama in 2016.\r\n\r\nDe Niro's first major film roles were in the sports drama, Bang the Drum Slowly (1973) and Scorsese's crime film Mean Streets (1973). He earned Academy Award nominations for the psychological thrillers Taxi Driver (1976) and Cape Fear (1991), both directed by Scorsese. De Niro received additional nominations for Michael Cimino's Vietnam war drama, The Deer Hunter (1978), Penny Marshall's drama Awakenings (1990), and David O. Russell's romantic comedy-drama, Silver Linings Playbook (2012).\r\n\r\nHis portrayal of gangster Jimmy Conway in Scorsese's crime film, Goodfellas (1990), and his role in black comedy film The King of Comedy (1983), earned him BAFTA Award nominations.\r\n\r\nDe Niro has earned four nominations for the Golden Globe Award for Best Actor – Motion Picture Musical or Comedy, for his work in the musical drama New York, New York (1977), the action comedy Midnight Run (1988), the gangster comedy Analyze This (1999), and the comedy Meet the Parents (2000). Other notable performances include roles in Once Upon a Time in America (1984), Brazil (1985), The Untouchables (1987), Heat (1995), Casino (1995), Cop Land (1997), Jackie Brown (1997) and Machete (2010). He has directed and starred in films such as the crime drama A Bronx Tale (1993), and the spy film The Good Shepherd (2006).	0	2018-10-16 00:55:12.794697-04
36909c84-3639-4c0b-8483-3ce1c027b7c3	Diane Keaton	https://i.imgur.com/uYd3TJs.jpg	Diane Keaton (born January 5, 1946) is an American film actress, director and producer. She began her career on stage and made her screen debut in 1970. Her first major film role was as Kay Adams-Corleone in The Godfather (1972), but the films that shaped her early career were those with director and co-star Woody Allen, beginning with Play It Again, Sam in 1972. Her next two films with Allen, Sleeper (1973) and Love and Death (1975), established her as a comic actor. Her fourth, Annie Hall (1977), won her the Academy Award for Best Actress.\r\n\r\nKeaton subsequently expanded her range to avoid becoming typecast as her Annie Hall persona. She became an accomplished dramatic performer, starring in Looking for Mr. Goodbar (1977) and received Academy Award nominations for Reds (1981), Marvin's Room (1996) and Something's Gotta Give (2003). Some of her popular later films include Baby Boom (1987), Father of the Bride (1991), The First Wives Club (1996), and The Family Stone (2005). Keaton's films have earned a cumulative gross of over US$1.1 billion in North America. In addition to acting, she is also a photographer, real estate developer, author, and occasional singer.	0	2018-10-16 00:55:12.794697-04
f9fa3df5-3268-48e4-9ecb-bdba589f503c	Clint Eastwood	https://i.imgur.com/mpda8MZ.jpg	Clinton \"Clint\" Eastwood, Jr. (born May 31, 1930) is an American actor, filmmaker, musician, and political figure. After earning success in the Western TV series Rawhide, he rose to international fame with his role as the Man with No Name in Sergio Leone's Dollars trilogy of spaghetti Westerns during the 1960s, and as antihero cop Harry Callahan in the five Dirty Harry films throughout the 1970s and 1980s. These roles, among others, have made Eastwood an enduring cultural icon of masculinity.\r\n\r\nFor his work in the Western film Unforgiven (1992) and the sports drama Million Dollar Baby (2004), Eastwood won Academy Awards for Best Director and Best Picture, as well as receiving nominations for Best Actor. Eastwood's greatest commercial successes have been the adventure comedy Every Which Way But Loose (1978) and its sequel, the action comedy Any Which Way You Can (1980), after adjustment for inflation. Other popular films include the Western Hang 'Em High (1968), the psychological thriller Play Misty for Me (1971), the crime film Thunderbolt and Lightfoot (1974), the Western The Outlaw Josey Wales (1976), the prison film Escape from Alcatraz (1979), the action film Firefox (1982), the suspense thriller Tightrope (1984), the Western Pale Rider (1985), the war films Where Eagles Dare (1968), Heartbreak Ridge (1986), the action thriller In the Line of Fire (1993), the romantic drama The Bridges of Madison County (1995), and the drama Gran Torino (2008).\r\n\r\nIn addition to directing many of his own star vehicles, Eastwood has also directed films in which he did not appear, such as the mystery drama Mystic River (2003) and the war film Letters from Iwo Jima (2006), for which he received Academy Award nominations, and the drama Changeling (2008). The war drama biopic American Sniper (2014) set box office records for the largest January release ever and was also the largest opening ever for an Eastwood film.\r\n\r\nEastwood received considerable critical praise in France for several films, including some that were not well received in the United States. Eastwood has been awarded two of France's highest honors: in 1994 he became a recipient of the Commander of the Ordre des Arts et des Lettres, and in 2007 he was awarded the Legion of Honour medal. In 2000, Eastwood was awarded the Italian Venice Film Festival Golden Lion for lifetime achievement.\r\n\r\nSince 1967, Eastwood has run his own production company, Malpaso, which has produced all except four of his American films. Starting in 1986, Eastwood served for two years as Mayor of Carmel-by-the-Sea, California, a non-partisan office.	0	2018-10-16 00:55:12.794697-04
57f35d8f-a27c-4195-818f-c411ca5f2171	Eli Wallach	https://i.imgur.com/RDM4lnx.jpg	Eli Herschel Wallach (December 7, 1915 - June 24, 2014) was an American film, television and stage actor whose career spanned more than six decades, beginning in the late 1940s. Trained in stage acting, which he enjoyed doing most, he became \"one of the greatest 'character actors' ever to appear on stage and screen\" states TCM, with over 90 film credits. On stage, he often co-starred with his wife, Anne Jackson, becoming one of the best-known acting couples in the American theater.\r\n\r\nWallach initially studied method acting under Sanford Meisner, and later became a founding member of the Actors Studio, where he studied under Lee Strasberg. His versatility gave him the ability to play a wide variety of different roles throughout his career, primarily as a supporting actor.\r\n\r\nFor his debut screen performance in Baby Doll, he won a BAFTA Award for Best Newcomer and a Golden Globe Award nomination. Among his other most famous roles are Calvera in The Magnificent Seven (1960), Guido in The Misfits (1961), and Tuco (\"The Ugly\") in The Good, the Bad and the Ugly (1966). Other notable portrayals include outlaw Charlie Gant in How The West Was Won (1962), Don Altobello in The Godfather Part III, Cotton Weinberger in The Two Jakes (both 1990), and Arthur Abbott in The Holiday (2006). One of America's most prolific screen actors, Wallach remained active well into his nineties, with roles as recently as 2010 in Wall Street: Money Never Sleeps and The Ghost Writer.\r\n\r\nWallach received BAFTA Awards, Tony Awards and Emmy Awards for his work, and received an Academy Honorary Award at the second annual Governors Awards, presented on November 13, 2010.	0	2018-10-16 00:55:12.794697-04
94561538-afc6-4ecc-a2e3-2ff3972e4958	Lee Van Cleef	https://i.imgur.com/IHYAXKw.jpg	Clarence Leroy \"Lee\" Van Cleef, Jr. (January 9, 1925 - December 16, 1989), was an American actor whose sinister features overshadowed his acting skills and typecast him as a minor villain for a decade before he achieved stardom in Spaghetti Westerns such as The Good, the Bad and the Ugly. Hatchet-faced with piercing eyes, he declined to have his hook nose altered to play a sympathetic character in his film debut, High Noon, and was relegated to a non-speaking outlaw as a result. Van Cleef had suffered serious injuries in a car crash, and had begun to lose interest in his apparently waning career by the time Sergio Leone gave him a major role in For a Few Dollars More. The film made him a box-office draw, especially in Europe.	0	2018-10-16 00:55:12.794697-04
1f65e0bc-daff-48d7-9c92-548aca079bcc	Tom Hanks	https://i.imgur.com/vW1xhq1.jpg	Thomas Jeffrey \"Tom\" Hanks (born July 9, 1956) is an American actor, comedian and filmmaker. He is known for his roles in Splash (1984), Big (1988), Turner & Hooch (1989), Philadelphia (1993), Forrest Gump (1994), Apollo 13 (1995), Saving Private Ryan, You've Got Mail (both 1998), The Green Mile (1999), Cast Away (2000), and The Da Vinci Code (2006), as well as for his voice work in the animated Toy Story series and The Polar Express (2004).\r\n\r\nHanks' films have grossed more than $4.4 billion at U.S. and Canadian box offices and more than $8.7 billion worldwide, making him the fourth highest-grossing actor in North America. Hanks has been nominated for numerous awards during his career. He won a Golden Globe Award and an Academy Award for Best Actor for his role in Philadelphia, as well as a Golden Globe, an Academy Award, a Screen Actors Guild Award, and a People's Choice Award for Best Actor for his role in Forrest Gump. In 2004, he received the Stanley Kubrick Britannia Award for Excellence in Film from the British Academy of Film and Television Arts (BAFTA). In 2014, he received a Kennedy Center Honor, and in 2016, he received a Presidential Medal of Freedom from President Barack Obama.\r\n\r\nHanks is also known for his collaborations with film director Steven Spielberg on Saving Private Ryan, Catch Me If You Can (2002), The Terminal (2004), and Bridge of Spies (2015), as well as the 2001 miniseries Band of Brothers, which launched Hanks as a successful director, producer, and screenwriter. In 2010, Spielberg and Hanks were executive producers on the HBO miniseries The Pacific (a companion piece to Band of Brothers).	0	2018-10-16 00:55:12.794697-04
529f1457-720a-4582-96a9-e26482c6f326	Louise Fletcher	https://i.imgur.com/v0HQmhz.png	Estelle Louise Fletcher (born July 22, 1934) is an Academy Award-winning, American film and television actress. She initially debuted in the television series Maverick in 1959. Also in 1959 she played a young mother on the tv series \"Wagon Train\" in, \"The Andrew Hale Story\", before being cast in Robert Altman's Thieves Like Us (1974). The following year, Fletcher gained international recognition for her performance as Nurse Ratched in the 1975 film One Flew over the Cuckoo's Nest, for which she won the Academy Award for Best Actress, BAFTA Award for Best Actress in a Leading Role and Golden Globe Award for Best Actress. She became only the third actress to win an Academy Award, BAFTA Award and Golden Globe Award for a single performance, after Audrey Hepburn and Liza Minnelli. Other notable film roles include Exorcist II: The Heretic (1977), Brainstorm (1983), Firestarter (1984), Flowers in the Attic (1987), 2 Days in the Valley (1996), and Cruel Intentions (1999).\r\n\r\nLater into her career, Fletcher returned to television, appearing as Kai Winn Adami in Star Trek: Deep Space Nine, as well as receiving Emmy nominations for her guest-starring roles in Picket Fences and Joan of Arcadia. Most recently, Fletcher has appeared in a recurring role on the Showtime television series Shameless in 2011 and 2012, as Frank Gallagher's foul-mouthed and hard-living mother who is serving a prison sentence for manslaughter.	0	2018-10-16 00:55:12.794697-04
b6c3c558-4973-4301-9fda-d5a6c3492bbb	Sally Field	https://i.imgur.com/QhTWtte.jpg	Sally Margaret Field (born November 6, 1946) is an American film and television actress and director. Field began her career in television, starring on the sitcoms Gidget (1965–66) and The Flying Nun (1967–70). She ventured into film with Smokey and the Bandit (1977) and later Norma Rae (1979), for which she received the Academy Award for Best Actress. She later received Golden Globe Award nominations for her performances in Absence of Malice (1981) and Kiss Me Goodbye (1982), before receiving her second Oscar for Best Actress for Places in the Heart (1984). Field received further nominations for a Golden Globe Award for Best Actress for Murphy's Romance (1985) and Steel Magnolias (1989).\r\n\r\nIn the 1990s, Field appeared in a wide range of films, including Not Without My Daughter (1991) and Mrs. Doubtfire (1993), before being nominated for the BAFTA Award for Best Actress in a Supporting Role for Forrest Gump (1994). In the 2000s, she returned to television with a recurring role on the NBC medical drama ER, for which she won the Primetime Emmy Award for Outstanding Guest Actress in a Drama Series in 2001. From 2006 to 2011, she portrayed the lead role of Nora Walker on the ABC drama Brothers & Sisters, for which she won the 2007 Primetime Emmy Award for Outstanding Lead Actress in a Drama Series. Field later starred as Mary Todd Lincoln in Lincoln (2012), for which she was nominated for the Academy Award for Best Supporting Actress and the Golden Globe Award for Best Supporting Actress, among other accolades. She also appeared as Aunt May in The Amazing Spider-Man (2012) and reprised the role in the 2014 sequel.\r\n\r\nField also directed the TV film The Christmas Tree (1996), an episode of the 1998 HBO miniseries From the Earth to the Moon as well as the feature film Beautiful (2000). In 2014, she was presented with a star on the Hollywood Walk of Fame.	0	2018-10-16 00:55:12.794697-04
bce2bdca-6824-4189-84d5-ba867374f3ac	Robin Wright	https://i.imgur.com/4k92kXo.jpg	Robin Gayle Wright (born April 8, 1966) is an American actress and director. She currently stars as Claire Underwood in the Netflix series House of Cards, for which she won the Golden Globe Award for Best Actress – Television Series Drama in 2014, making her the first actress to win a Golden Globe for a web television series. Wright received Primetime Emmy nominations for House of Cards in 2013, 2014, 2015 and 2016.\r\n\r\nWright first gained attention for her role in the NBC Daytime soap opera Santa Barbara, as Kelly Capwell from 1984 to 1988. She then made the transition to film, starring in the romantic comedy fantasy adventure film The Princess Bride (1987). She has since enjoyed a successful career in the film industry, starring in various films, including the epic romantic comedy-drama Forrest Gump (1994), the romantic drama Message in a Bottle (1999), the superhero drama-thriller Unbreakable (2000), the historical drama The Conspirator (2010), the biographical sports drama Moneyball (2011), the mystery thriller The Girl with the Dragon Tattoo (2011), and the biographical drama Everest (2015). She will next appear in the superhero film Wonder Woman (2017) and will appear in the upcoming film Blade Runner 2049 opposite Harrison Ford and Ryan Gosling, slated for a 2017 release.	0	2018-10-16 00:55:12.794697-04
abbc43fa-5ef3-4f3e-871e-eeb45e2bed3a	Gary Sinise	https://i.imgur.com/4VIDlit.jpg	Gary Alan Sinise (born March 17, 1955) is an American actor, director and musician. Among other awards, he has won an Emmy Award, a Golden Globe Award, and has been nominated for an Academy Award.\r\n\r\nSinise is known for several memorable roles. These include George Milton in Of Mice and Men, Lieutenant Dan Taylor in Forrest Gump (for which he was nominated for the Academy Award for Best Supporting Actor), Harry S. Truman in Truman (for which he won a Golden Globe), Ken Mattingly in Apollo 13, Detective Jimmy Shaker in Ransom, Detective Mac Taylor in the CBS series CSI: NY (2004–13), and George C. Wallace in the television film George Wallace (for which he won an Emmy). In 2016, Sinise began starring in Criminal Minds: Beyond Borders.	0	2018-10-16 00:55:12.794697-04
c2d15ac2-a72c-41d5-97cd-10bc35d859bc	Mykelti Williamson	https://i.imgur.com/EbHNRGa.jpg	Michael T. \"Mykelti\" Williamson (born March 4, 1957) is an American actor best known for his roles in the films Forrest Gump and Con Air, and the television shows Boomtown, 24, and Justified.\r\n\r\nHis other notable roles include Free Willy, Heat, Lucky Number Slevin, Three Kings, Black Dynamite, and The Final Destination.	0	2018-10-16 00:55:12.794697-04
ed43abe2-1d70-42cc-8cd5-78d3d1adc04c	Jack Nicholson	https://i.imgur.com/CtmXuOn.jpg	John Joseph \"Jack\" Nicholson (born April 22, 1937) is an American actor and filmmaker, who has performed for nearly 60 years. Nicholson is known for playing a wide range of starring or supporting roles, including satirical comedy, romance and dark portrayals of excitable and psychopathic characters. In many of his films, he has played the \"eternal outsider, the sardonic drifter\", and someone who rebels against the social structure.\r\n\r\nNicholson's 12 Academy Award nominations make him the most nominated male actor in the Academy's history. Nicholson has won the Academy Award for Best Actor twice, one for the drama One Flew Over the Cuckoo's Nest (1975) and the other for the romantic comedy As Good as It Gets (1997). He also won the Academy Award for Best Supporting Actor for the comedy-drama Terms of Endearment (1983). Nicholson is one of three male actors to win three Academy Awards.\r\n\r\nNicholson is one of only two actors to be nominated for an Academy Award for acting in every decade from the 1960s to the 2000s; the other is Michael Caine. He has won six Golden Globe Awards, and received the Kennedy Center Honor in 2001. In 1994, he became one of the youngest actors to be awarded the American Film Institute's Life Achievement Award.\r\n\r\nOther films in which he has starred include the road movie Easy Rider (1969), the drama Five Easy Pieces (1970), the comedy-drama film The Last Detail (1973), the neo-noir mystery film Chinatown (1974), the drama The Passenger (1975), and the epic film Reds (1981).\r\n\r\nHe played Jack Torrance in Stanley Kubrick's horror film The Shining (1980), the Joker in Batman (1989), and Frank Costello in Martin Scorsese's neo-noir crime drama The Departed (2006). Other films include the legal drama A Few Good Men (1992), the Sean Penn-directed mystery film The Pledge (2001), and the comedy-drama About Schmidt (2002).	0	2018-10-16 00:55:12.794697-04
d9ef8ce8-47f8-4864-b0a4-ffaf082c7a49	Danny DeVito	https://i.imgur.com/WWDdVoO.jpg	Daniel Michael \"Danny\" DeVito, Jr. (born November 17, 1944) is an American actor, comedian, director and producer. He gained prominence for his portrayal of the taxi dispatcher Louie De Palma in Taxi (1978-1983) which won him a Golden Globe and an Emmy.\r\n\r\nA major film star, he is known for his roles in Tin Men, Throw Momma from the Train, One Flew Over the Cuckoo's Nest, Ruthless People, Man on the Moon, Terms of Endearment, Romancing the Stone, Twins, Batman Returns, Other People's Money, Get Shorty and L.A. Confidential and for his voiceover in such films as Space Jam, Hercules and The Lorax.\r\n\r\nDeVito and Michael Shamberg founded Jersey Films. Soon afterwards, Stacey Sher became an equal partner. The production company is known for films such as Pulp Fiction, Garden State, and Freedom Writers. DeVito also owns Jersey Television, which produced the Comedy Central series Reno 911!. DeVito and wife Rhea Perlman starred together in his 1996 film Matilda, based on Roald Dahl's children's novel. DeVito was also one of the producers nominated for an Academy Award for Best Picture for Erin Brockovich.\r\n\r\nHe currently stars as Frank Reynolds on the FXX sitcom It's Always Sunny in Philadelphia. He directs, produces and appears in graphic, short, horror films for his Internet venture The Blood Factory.\r\n\r\nDeVito's short stature is the result of multiple epiphyseal dysplasia (Fairbank's disease), a rare genetic disorder that affects bone growth in those afflicted.	0	2018-10-16 00:55:12.794697-04
6c35668a-7c8b-4351-bd3e-dd5b402c4975	Christopher Lloyd	https://i.imgur.com/IAneYME.jpg	Christopher Allen Lloyd (born October 22, 1938) is an American actor, voice actor and comedian best known for his roles as Emmett \"Doc\" Brown in the Back to the Future trilogy, Judge Doom in Who Framed Roger Rabbit (1988) and Uncle Fester in The Addams Family (1991) and its sequel Addams Family Values (1993).\r\n\r\nLloyd has an equally prominent television profile, having won two Primetime Emmy Awards for playing Jim Ignatowski on the comedy series Taxi (1978-1983). He earned a third Emmy for his 1992 guest appearance on Road to Avonlea. He has also done extensive voiceover work for animated programs, most notably voicing The Hacker on the PBS Kids series Cyberchase (2002-2015). The role earned him two Daytime Emmy Award nominations.	0	2018-10-16 00:55:12.794697-04
6e07836e-3883-475e-982d-692dee2a1017	Rumi Hiiragi	https://i.imgur.com/h8ahGD7.jpg	Rumi Hiiragi (born August 1, 1987) is a Japanese actress.\r\n\r\nHiiragi began her career as a child actress while at the age of six, appearing in numerous commercials. In 1999, she appeared in the NHK asadora Suzuran, portraying the main character, Moe Tokiwa. In 2001, she worked on Hayao Miyazaki's award-winning anime film Spirited Away, in which she voiced the film's protagonist, Chihiro, for which her performance was praised. She returned to Ghibli as a voice in their films, Ponyo on the Cliff by the Sea and From up on Poppy Hill.\r\n\r\nIn 2002, she appeared in the high school baseball television program Netto Koshien as a field reporter. In 2005, she appeared in the NTV program Nobuta o Produce, portraying the character Kasumi Aoi.	0	2018-10-16 00:55:12.794697-04
7a6b4aee-78d3-4508-a6c7-86283c8805db	Miyu Irino	https://i.imgur.com/Qic2X9C.png	Miyu Irino (born February 19, 1988) is a Japanese voice actor born in Tokyo. He voices the lead character Sora in the Kingdom Hearts video game series. His major anime voice roles include Todomatsu Matsuno in Osomatsu-san, Haku in Spirited Away, Daisuke Niwa in D.N.Angel, Syaoran Li in Tsubasa Reservoir Chronicle, Sena Kobayakawa in Eyeshield 21, Astral in Yu-Gi-Oh! Zexal, Jinta in Anohana: The Flower We Saw That Day and Yuichiro Hyakuya in Seraph of the End. On June 26, 2009, he released his debut mini-album called Soleil, and in August 2009, Irino played a leading role in the movie Monochrome Girl. His first single album, Faith, was released on November 25, 2009.	0	2018-10-16 00:55:12.794697-04
ea144112-e023-43ed-841c-4577af9b2515	Mari Natsuki	https://i.imgur.com/qhnuTEM.png	Junko Nakajima (born May 2, 1952), more commonly known by her stage name Mari Natsuki, is a Japanese singer, dancer and actress. Born in Tokyo, she started work as a singer from a young age. In 2007, Natsuki announced her engagement to percussionist Nobu Saitou, with their marriage taking place in Spring 2008.\r\n\r\nNatsuki has participated in musical theatre, including that of Yukio Ninagawa. She provided the voice of Yubaba in Spirited Away, played the young witch's mother in the Japanese TV remake of Bewitched, and has twice been nominated for a Japanese Academy Award. Natsuki played the character Big Mama in the Japanese version of Metal Gear Solid 4: Guns of the Patriots and has also acted in television dramas, such as the 2005 series Nobuta o Produce, playing the Vice Principal, Katharine.	0	2018-10-16 00:55:12.794697-04
a1f926ad-2187-47f4-abb8-fce671e5fd93	Bunta Sugawara	https://i.imgur.com/OZeuqf6.jpg	Bunta Sugawara (August 16, 1933 - November 28, 2014) was a Japanese actor who appeared in almost 200 feature films. He is the father of one son, actor Kaoru Sugawara, and two daughters.\r\n\r\nBunta Sugawara was born in Sendai in 1933. His parents divorced when he was four, and he moved to Tokyo to live with his father and stepmother. As part of a wartime policy to evacuate children from major cities, he was moved back to Sendai during fourth grade. As an adult he entered Waseda University's law program, but was dropped in his second year for failing to pay and began work as a model in 1956.\r\n\r\nHis first acting role was in the 1956 Toho film Aishu no Machi ni Kiri ga Furu. Sugawara appeared in Teruo Ishii's 1958 White Line after being scouted by the Shintoho studio. At Shintoho he gained starring roles despite being a newcomer. However, when Shintoho filed for bankruptcy in 1961, Sugawara moved to the Shochiku studio where he was cast in Masahiro Shinoda's Shamisen and Motorcycle, but was fired from the role for coming to set late after a night drinking. He gave a notable performance in Keisuke Kinoshita's Legend of a Duel to the Death (1963), but it did not fare well at the box office. Disenchanted with the low pay, and what he felt were unsuitable roles, he left and went to Toei in 1967 after being recommended by Noboru Ando.\r\n\r\nHe had a part in Ishii's 1967 Abashiri Bangaichi: Fubuki no Toso, one of many films in the director's Abashiri Prison series. Sugawara's first starring role at Toei was in Gendai Yakuza: Yotamono no Okite in 1969. It launched a series, with the last installment, 1972's Street Mobster by Kinji Fukasaku, being the most successful. He achieved major success in 1973 at the age of 40, when he starred in Fukasaku's five-part yakuza epic Battles Without Honor and Humanity. Based on a real-life yakuza conflict in Hiroshima, the series was very successful, and popularized a new type of yakuza film called the Jitsuroku eiga, and the role of Shouzou Hirono still remains his most well known. Sugawara also starred in Fukasaku's Cops vs. Thugs in 1975. Also in 1975, he starred in the comedy Torakku Yarou: Go-Iken Muyou as a love-seeking truck driver, which launched a successful ten-installment series. Sugawara won the 1980 Japan Academy Prize for Best Supporting Actor for his role as a detective in Kazuhiko Hasegawa's 1979 satirical film Taiyou o Nusunda Otoko.\r\n\r\nHis son Kaoru died in a railroad crossing accident in October 2001.\r\n\r\nOn February 23, 2012, Sugawara announced his retirement from acting. He came to the decision after the Great East Japan earthquake and being hospitalized in the winter of 2011, although he said he might consider future roles. Late in life, he took up farming in Yamanashi Prefecture.\r\n\r\nOn December 1, 2014, it was announced that Sugawara had died from liver cancer in a Tokyo hospital on November 28, 2014.	0	2018-10-16 00:55:12.794697-04
3999ec01-7f33-4dbd-9318-54b467a340ba	Tatsuya Gashuin	https://i.imgur.com/rEslV4T.png	Tatsuya Gashuin (born December 10, 1950) is a Japanese actor, known for his roles in Spirited Away (2001), Howl's Moving Castle (2004) and The Taste of Tea (2004).	0	2018-10-16 00:55:12.794697-04
05758487-9707-4807-8aba-d06f275cebdb	Joseph Gordon-Levitt	https://i.imgur.com/xXYWqrn.jpg	Joseph Leonard Gordon-Levitt (born February 17, 1981) is an American actor and filmmaker. As a child actor, he appeared in the films A River Runs Through It, Angels in the Outfield and 10 Things I Hate About You, and as Tommy Solomon in the TV series 3rd Rock from the Sun. He took a break from acting to study at Columbia University, but dropped out in 2004 to pursue acting again. He has since starred in 500 Days of Summer, Inception, Hesher, 50/50, Premium Rush, The Dark Knight Rises, Brick, Looper, The Lookout, Manic, Lincoln, Mysterious Skin and G.I. Joe: The Rise of Cobra. He starred as Philippe Petit in the Robert Zemeckis-directed film The Walk (2015), and as Edward Snowden in the Oliver Stone film Snowden (2016).\r\n\r\nHe also founded the online production company hitRECord in 2004 and has hosted his own TV series, HitRecord on TV, since January 2014. In 2013, Gordon-Levitt made his feature film directing and screenwriting debut with Don Jon, a comedy film in which he also stars. He previously directed and edited two short films, both of which were released in 2010: Morgan M. Morgansen's Date with Destiny and Morgan and Destiny's Eleventeenth Date: The Zeppelin Zoo.	1	2018-10-16 00:55:12.794697-04
04b36dba-8e4a-4dc0-961b-ff0a1cbd05e0	William Redfield	https://i.imgur.com/sSlpe63.jpg	William Redfield (January 26, 1927 - August 17, 1976) was an American actor and author who appeared in numerous theatrical, film, radio, and television roles.	0	2018-10-16 00:55:12.794697-04
2efda8f6-5975-4455-8dc0-d6a47e859176	Brad Pitt	https://i.imgur.com/tEr10ut.jpg	William Bradley \"Brad\" Pitt (born December 18, 1963) is an American actor and producer. He has received multiple awards and nominations including an Academy Award as producer under his own company Plan B Entertainment.\r\n\r\nPitt first gained recognition as a cowboy hitchhiker in the road movie Thelma & Louise (1991). His first leading roles in big-budget productions came with the dramas A River Runs Through It (1992) and Legends of the Fall (1994), and Interview with the Vampire (1994). He gave critically acclaimed performances in the crime thriller Seven and the science fiction film 12 Monkeys (both 1995), the latter earning him a Golden Globe Award for Best Supporting Actor and an Academy Award nomination. Pitt starred in the cult film Fight Club (1999) and the major international hit Ocean's Eleven (2001) and its sequels, Ocean's Twelve (2004) and Ocean's Thirteen (2007). His greatest commercial successes have been Troy (2004), Mr. & Mrs. Smith (2005), and World War Z (2013). Pitt received his second and third Academy Award nominations for his leading performances in The Curious Case of Benjamin Button (2008) and Moneyball (2011). He produced The Departed (2006) and 12 Years a Slave (2013), both of which won the Academy Award for Best Picture, and also The Tree of Life, Moneyball, and The Big Short (2015), all of which garnered Best Picture nominations.\r\n\r\nAs a public figure, Pitt has been cited as one of the most influential and powerful people in the American entertainment industry, as well as the world's most attractive man, by various media outlets. His personal life is also the subject of wide publicity. Divorced from actress Jennifer Aniston, to whom he was married for five years, he has been married to actress Angelina Jolie since 2014. They have six children together, three of whom were adopted internationally. In September 2016, Jolie filed for divorce from Pitt.	1	2018-10-16 00:55:12.794697-04
45d7716d-5301-4b24-bb53-9aa7ef7f1866	Henry Fonda	https://i.imgur.com/iP0HJ09.jpg	Henry Jaynes Fonda (May 16, 1905 - August 12, 1982) was a celebrated American film and stage actor with a career spanning more than five decades.\r\n\r\nFonda made his mark early as a Broadway actor. He also appeared in 1938 in plays performed in White Plains, New York, with Joan Tompkins. He made his Hollywood debut in 1935, and his career gained momentum after his Academy Award-nominated performance as Tom Joad in The Grapes of Wrath, a 1940 adaptation of John Steinbeck's novel about an Oklahoma family who moved west during the Dust Bowl.\r\n\r\nThroughout six decades in Hollywood, Fonda cultivated a strong, appealing screen image in such classics as The Ox-Bow Incident, Mister Roberts and 12 Angry Men. Later, Fonda moved both toward darker epics such as Sergio Leone's Once Upon a Time in the West and lighter roles in family comedies such as Yours, Mine and Ours with Lucille Ball, winning the Academy Award for Best Actor at the 54th Academy Awards for the movie On Golden Pond, his final film role.\r\n\r\nFonda was the patriarch of a family of famous actors, including daughter Jane Fonda, son Peter Fonda, granddaughter Bridget Fonda, and grandson Troy Garity. His family and close friends called him \"Hank\". In 1999, he was named the sixth-Greatest Male Star of All Time by the American Film Institute.	1	2018-10-16 00:55:12.794697-04
e9ff6b3d-461c-49c5-af29-25f367a0a3ab	Chris Evans	https://i.imgur.com/TCMNXRn.jpg	Christopher Robert \"Chris\" Evans (born June 13, 1981) is an American actor and filmmaker. Evans is known for his superhero roles as the Marvel Comics characters Captain America in the Marvel Cinematic Universe and the Human Torch in Fantastic Four and its 2007 sequel.\r\n\r\nHe began his career on the 2000 television series Opposite Sex, and has since appeared in a number of films, such as Not Another Teen Movie, Sunshine, Scott Pilgrim vs. the World, Snowpiercer and the upcoming Gifted. In 2014, Evans made his directorial debut with the drama film Before We Go, in which he also starred. 	0	2018-10-16 00:55:12.794697-04
4bbd0711-7db1-4bba-9e3e-09ba7e50e9a1	Donnie Yen	https://i.imgur.com/FhNubJG.jpg	Donnie Yen (born 27 July 1963), is a Hong Kong actor, Chinese martial artist, film director and producer, action choreographer, and multiple-time world wushu tournament champion.\r\n\r\nYen is credited by many for contributing to the popularization of the traditional martial arts style known as Wing Chun. He played Wing Chun grandmaster Yip Man in the 2008 film Ip Man, which was a box office success. This has led to an increase in the number of people taking up Wing Chun, leading to hundreds of new Wing Chun schools being opened up in mainland China and other parts of Asia. Ip Chun, the eldest son of Ip Man, even mentioned that he is grateful to Yen for making his family art popular and allowing his father's legacy to be remembered.\r\n\r\nYen is considered to be one of Hong Kong's top action stars; director Peter Chan mentioned that he \"is the 'it' action person right now\" and \"has built himself into a bona fide leading man, who happens to be an action star.\" Yen is widely credited for bringing mixed martial arts (MMA) into the mainstream of Chinese culture, by choreographing MMA in many of his recent films. Yen has displayed notable skills in a wide variety of martial arts, being well-versed in Tai chi chuan, boxing, kickboxing, Jeet Kune Do, Hapkido, Taekwondo, karate, Muay Thai, wrestling, Brazilian Jiu-Jitsu, Judo, Wing Chun, and Wushu. Seen as one of the most popular film stars in Asia in recent years, Yen was one of the highest paid actors in Asia in 2009. Yen earned 220 million HKD (28.4 million USD) from four films and six advertisements in 2013.	1	2018-10-16 00:55:12.794697-04
\.


--
-- TOC entry 2869 (class 0 OID 16460)
-- Dependencies: 200
-- Data for Name: casting; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.casting (casting_id, actor_id, movie_id, character_name) FROM stdin;
d6214bcd-378f-4f2d-992c-51a4ffeab4b1	416930d1-8ce1-469a-8786-9ef616e72d51	b78281d4-8b0e-40d1-b5d6-d51f895d2962	\N
d47b7d76-a82c-4ca7-a5f7-594d568cfc94	05758487-9707-4807-8aba-d06f275cebdb	f83eb6f1-d947-405a-97b1-bcf39f681b71	\N
3a5da812-85c4-4c4c-b377-2a76c7944f63	2efda8f6-5975-4455-8dc0-d6a47e859176	a2714e08-27a2-4805-b0c3-1a5e3b3933cd	\N
2870fa86-a78c-4653-b67e-3ba538e222ac	d977e8d6-c3f4-4469-b9c4-a6152b273ab2	22c67054-30be-42c2-9bfb-4a56b28a578a	\N
4bf9894c-90c4-4769-b8db-6d11b53c0b72	80a368c1-112e-4c88-bf15-af46d9611fbc	22c67054-30be-42c2-9bfb-4a56b28a578a	\N
21e0cfe3-d3b1-4b9d-9e57-30524338573c	4bbd0711-7db1-4bba-9e3e-09ba7e50e9a1	22c67054-30be-42c2-9bfb-4a56b28a578a	\N
898585b4-e955-4de4-bf1f-579fb8951f38	950491ff-b7fb-4e8b-9e25-0b1cd9a1c91b	22c67054-30be-42c2-9bfb-4a56b28a578a	\N
593b055d-4c0e-4d7a-852d-dad51064cd68	cf733300-bb88-4743-8b40-78ff96d242c0	22c67054-30be-42c2-9bfb-4a56b28a578a	\N
816c86a9-73dd-4cdb-a797-f459f4ede279	d10c0ab5-1422-4b41-8ba8-9d415c0d7be7	22c67054-30be-42c2-9bfb-4a56b28a578a	\N
6f7cb675-1fa0-45c3-9bd1-30b2729072e1	d9019a6b-f84c-44d1-9bd7-e6ae1e750a06	172e7dcb-583d-4b2e-9533-94684fe794b0	\N
5411792d-62bb-43bc-beca-d7719f22f297	1fc483d5-cc50-407b-a053-6754ba2e1d97	172e7dcb-583d-4b2e-9533-94684fe794b0	\N
88b3cc6c-03ca-4fdb-ac3a-9cd233dd8dab	54230d77-4594-4412-ac28-1186eb5578ad	172e7dcb-583d-4b2e-9533-94684fe794b0	\N
c5c4a0bc-280f-4ad8-bd1a-0b783ca9dfcc	b929faa5-2b77-48d9-97e3-12f5c9f41732	172e7dcb-583d-4b2e-9533-94684fe794b0	\N
fc1aadeb-eac6-480a-ad5d-ef5a8347723c	80331bf3-89af-4dfc-85c8-e88ad19b4e98	172e7dcb-583d-4b2e-9533-94684fe794b0	\N
26583897-782c-449c-8327-870f6e3da078	e9ff6b3d-461c-49c5-af29-25f367a0a3ab	6159b6e4-3c17-4dd0-8923-174a5fc9a9e3	\N
e952be94-0ea6-4824-9e03-327b64e98a22	f9cf7043-64b2-4846-aa05-b6e4b6875886	6159b6e4-3c17-4dd0-8923-174a5fc9a9e3	\N
0fd78f1b-4654-49f8-85d9-8ae176065e2f	270153d8-4a4b-4462-a936-fdb1acf7304a	6159b6e4-3c17-4dd0-8923-174a5fc9a9e3	\N
258e627c-4206-4e55-8adb-f39f34ace587	8e14b32e-42f3-4fbf-80db-cc65c211679c	6159b6e4-3c17-4dd0-8923-174a5fc9a9e3	\N
64dadf32-3ef6-4d9b-b19b-02a4bf80edcc	d4d75d21-2a04-4a0d-b98c-bb010db27fd4	6159b6e4-3c17-4dd0-8923-174a5fc9a9e3	\N
bc24c3c5-c114-4e40-bd71-0f73a875d277	96c888c9-cf1e-4ddc-92ea-bfdef3d5d0d7	01d74d71-9dfb-4314-9d05-2f736a35f5c8	\N
b19b49a1-e034-4acb-bad0-1e2c071a45c6	c4926dde-da85-4b8c-a522-9f4c5e5b68ff	01d74d71-9dfb-4314-9d05-2f736a35f5c8	\N
2d264ab7-b60f-4a1a-b8f2-489fa684e82a	1a4599b9-799c-4745-8aff-92bff21f4df9	01d74d71-9dfb-4314-9d05-2f736a35f5c8	\N
ba940658-0a68-4125-a314-8ff55ab14a20	10f93de5-d4bd-48d0-af94-f41f6189fe0a	01d74d71-9dfb-4314-9d05-2f736a35f5c8	\N
d814e897-eb5d-4a5e-a233-896f5130ab90	26981d94-f045-4d3f-9eec-3ff2e0f82922	01d74d71-9dfb-4314-9d05-2f736a35f5c8	\N
fd7b8baf-429c-4713-be6d-b890eecc866d	f48c7da2-2e0c-4a7e-aeaa-3b85c9853011	f83eb6f1-d947-405a-97b1-bcf39f681b71	\N
f6422362-5e6c-4afe-8afb-59bb0f213f63	2ceec419-f3f7-4ced-8496-2c6522305f7c	f83eb6f1-d947-405a-97b1-bcf39f681b71	\N
75e528d3-88ff-48cc-bd72-3d2819bffd5e	f0094793-6c9d-4750-9ab0-a52de7862484	f83eb6f1-d947-405a-97b1-bcf39f681b71	\N
3aece6f2-60b7-4231-a494-3dcc69bc0816	23b7eb21-38f4-4ac2-8cfc-5f50d2a391c3	f83eb6f1-d947-405a-97b1-bcf39f681b71	\N
effd80a3-a23f-4a2e-ae3b-49c6d37f9b8a	eeb8d4f7-34fb-4959-8ed0-fb752922bd2e	b78281d4-8b0e-40d1-b5d6-d51f895d2962	\N
00b498d6-69a2-4bff-b5eb-347556342793	23bd9ad3-1c6a-4321-b2fb-441b737e391a	b78281d4-8b0e-40d1-b5d6-d51f895d2962	\N
753d5a3a-ac19-491e-8ea0-f021ff76739f	1cadcc67-67e2-468e-8ea5-19fdb211d56d	b78281d4-8b0e-40d1-b5d6-d51f895d2962	\N
e767e8be-7ca5-4044-a6d6-ea6717949cfa	b929faa5-2b77-48d9-97e3-12f5c9f41732	b78281d4-8b0e-40d1-b5d6-d51f895d2962	\N
967e7b24-b081-4051-90a1-c163ed9e3d70	54b25739-4f94-44ef-a1cf-06dcb144d2c8	a2714e08-27a2-4805-b0c3-1a5e3b3933cd	\N
5a3ce112-d921-4b34-9029-26ad55a810ed	0d00f5af-0415-4c76-b594-5a9ef9ffb637	a2714e08-27a2-4805-b0c3-1a5e3b3933cd	\N
e75fb987-00a9-44b6-a0ea-8d7d7ad229b0	a6d16ef3-3872-4e27-bdbe-b06c48800e97	a2714e08-27a2-4805-b0c3-1a5e3b3933cd	\N
ed12d720-99e6-494e-b8ca-07bbc9edff9a	f5ba9e8a-d4cb-4de7-9baa-4234ab7b77f8	a2714e08-27a2-4805-b0c3-1a5e3b3933cd	\N
71498a43-b547-4e87-b04b-2b688a9421c6	96c888c9-cf1e-4ddc-92ea-bfdef3d5d0d7	c66cd1c8-5e52-4a1e-a7dc-8788541fdd8b	\N
83f0ab7b-edd0-49f8-bc0d-94c1f60e084d	c4926dde-da85-4b8c-a522-9f4c5e5b68ff	c66cd1c8-5e52-4a1e-a7dc-8788541fdd8b	\N
af0ebbb3-e3b1-4836-9b8c-b775547b5ab3	1a4599b9-799c-4745-8aff-92bff21f4df9	c66cd1c8-5e52-4a1e-a7dc-8788541fdd8b	\N
1a3d191b-18d5-4687-9fed-575bce41db97	26981d94-f045-4d3f-9eec-3ff2e0f82922	c66cd1c8-5e52-4a1e-a7dc-8788541fdd8b	\N
601dc0ab-8165-40ff-b1c7-073a563cee9f	1cb2533f-ff15-4e7d-a3e4-652445c95e75	c66cd1c8-5e52-4a1e-a7dc-8788541fdd8b	\N
1aec1342-8a3a-4c98-90b6-6c5336431c1f	54b25739-4f94-44ef-a1cf-06dcb144d2c8	bf5acf5b-2f85-4056-aec1-24c6f57b4416	\N
1f4c7f8d-e36c-4f1e-a520-c22e430cc17b	157f1600-b8cc-4222-82da-d01908255516	bf5acf5b-2f85-4056-aec1-24c6f57b4416	\N
5e103945-204c-401d-b378-2a521dd07441	6671f1b7-ecef-426a-aa0c-266a0f187706	bf5acf5b-2f85-4056-aec1-24c6f57b4416	\N
7e0e55d4-ca0c-47d8-8518-828bb02c524d	a7de905e-6b6d-48c1-a570-0dc4d4d2264d	bf5acf5b-2f85-4056-aec1-24c6f57b4416	\N
df71acb7-1b12-4567-9fd0-f2bfca2fca88	30a3290b-8975-4567-97af-b6073a642883	bf5acf5b-2f85-4056-aec1-24c6f57b4416	\N
03944653-0c80-4f65-859a-68ce23502240	3408a4bc-56ee-470e-b002-c735ddd7d5d2	7f5d368a-be44-4ac9-9408-fcf7903e99e9	\N
8e3af41b-ae9d-42f6-aacd-eb9884c078ad	7566a5f2-45c6-4019-8e97-6894ea51156f	7f5d368a-be44-4ac9-9408-fcf7903e99e9	\N
373ccf32-882b-4f6f-ae1a-59797ab33e8f	47550a92-cb2e-4246-82a7-07838e516c60	7f5d368a-be44-4ac9-9408-fcf7903e99e9	\N
de24671c-8903-4b34-8250-8fd38f592af3	9ef521a3-757f-464e-8780-18dc2c2fdabc	7f5d368a-be44-4ac9-9408-fcf7903e99e9	\N
4c7f931b-3edc-4d42-8851-4f056c3fc312	19e9f98a-f3c7-4d0d-b4c1-90d51ccfc6cc	7f5d368a-be44-4ac9-9408-fcf7903e99e9	\N
215c099b-05a5-4ca0-9d5d-19df7d9c0b42	2fa869ad-358b-4b69-8d3d-5db38592f0de	fddf0e73-2aec-4de3-8d9c-e2ba8c8c1dae	\N
07b79a96-9c9b-4b5c-afd8-55ec37aa9a27	3408a4bc-56ee-470e-b002-c735ddd7d5d2	fddf0e73-2aec-4de3-8d9c-e2ba8c8c1dae	\N
3f0cc8ac-14a2-47b1-8f94-2053b641de5c	47550a92-cb2e-4246-82a7-07838e516c60	fddf0e73-2aec-4de3-8d9c-e2ba8c8c1dae	\N
71446b8a-2017-4458-bcba-bc5b99276884	19e9f98a-f3c7-4d0d-b4c1-90d51ccfc6cc	fddf0e73-2aec-4de3-8d9c-e2ba8c8c1dae	\N
8285ba07-1026-4b32-83e7-2efc56659259	36909c84-3639-4c0b-8483-3ce1c027b7c3	fddf0e73-2aec-4de3-8d9c-e2ba8c8c1dae	\N
8b76119e-19c8-4644-8dc9-f01474d78cec	45d7716d-5301-4b24-bb53-9aa7ef7f1866	da9757e8-610e-43fb-95dc-3a59b93bdfd0	\N
5db98fa2-9844-4751-89eb-312c24260401	78377bd7-4ce8-43f4-befb-a5ddd726b7b5	da9757e8-610e-43fb-95dc-3a59b93bdfd0	\N
2d67f863-d796-4b5d-9b46-8cef9fc27b67	164ae33d-5b15-46dd-9e4a-064b5087c545	da9757e8-610e-43fb-95dc-3a59b93bdfd0	\N
797329ff-c32c-4041-81c6-11933a8044bd	ec89ae36-1f13-4405-a664-dfa69a662096	da9757e8-610e-43fb-95dc-3a59b93bdfd0	\N
38044cbd-2f15-4e0c-809c-e4a3758a7912	2f635a08-126a-4b0f-ade4-839ac788aab3	da9757e8-610e-43fb-95dc-3a59b93bdfd0	\N
9c69628d-9d12-446d-8e62-3973b2de3f4f	f9fa3df5-3268-48e4-9ecb-bdba589f503c	20dbbcb7-6978-4792-bed7-4924ccb8b915	\N
ce21a4e9-54e3-4aaf-b1d4-b06fa9805c70	57f35d8f-a27c-4195-818f-c411ca5f2171	20dbbcb7-6978-4792-bed7-4924ccb8b915	\N
bda419f6-7ddb-498c-8d2e-60a33fbbe4ad	94561538-afc6-4ecc-a2e3-2ff3972e4958	20dbbcb7-6978-4792-bed7-4924ccb8b915	\N
f06e7f30-e9a5-469c-a055-dcc0898fb39e	1f65e0bc-daff-48d7-9c92-548aca079bcc	cb98c0ca-7ea2-4388-ba39-b92fe294bf7c	\N
2c87dcec-1189-42da-8c73-7735214bdf24	b6c3c558-4973-4301-9fda-d5a6c3492bbb	cb98c0ca-7ea2-4388-ba39-b92fe294bf7c	\N
9336a832-de7f-4d3b-854f-e6a3730f180f	bce2bdca-6824-4189-84d5-ba867374f3ac	cb98c0ca-7ea2-4388-ba39-b92fe294bf7c	\N
31e764b1-595f-4b9f-b93d-344ee5c34e7d	abbc43fa-5ef3-4f3e-871e-eeb45e2bed3a	cb98c0ca-7ea2-4388-ba39-b92fe294bf7c	\N
382a8651-ff63-4f06-b44f-57dab9fcf797	c2d15ac2-a72c-41d5-97cd-10bc35d859bc	cb98c0ca-7ea2-4388-ba39-b92fe294bf7c	\N
dfb88e1d-0626-433f-8c91-682319e5dec2	ed43abe2-1d70-42cc-8cd5-78d3d1adc04c	0759e092-c85f-4ac3-a501-fc2e42d82dd1	\N
ac81531b-64ef-4bc9-bb9f-c3e137cba25b	529f1457-720a-4582-96a9-e26482c6f326	0759e092-c85f-4ac3-a501-fc2e42d82dd1	\N
473a9ffa-e39b-43b8-9519-182f7d724b30	d9ef8ce8-47f8-4864-b0a4-ffaf082c7a49	0759e092-c85f-4ac3-a501-fc2e42d82dd1	\N
ba6885e8-c71e-47a8-9633-6d88b9bcdc7e	6c35668a-7c8b-4351-bd3e-dd5b402c4975	0759e092-c85f-4ac3-a501-fc2e42d82dd1	\N
3d974599-e6f9-4068-a156-aed0699b93a8	04b36dba-8e4a-4dc0-961b-ff0a1cbd05e0	0759e092-c85f-4ac3-a501-fc2e42d82dd1	\N
f7157136-3d39-48e3-8bc6-bd48475cee7d	6e07836e-3883-475e-982d-692dee2a1017	d43a4712-e5ba-4c0a-ac20-fa726bb93f49	\N
79702111-20a2-411d-881e-e5b5e9c4dd69	7a6b4aee-78d3-4508-a6c7-86283c8805db	d43a4712-e5ba-4c0a-ac20-fa726bb93f49	\N
c4aa5dda-f6b7-4376-a3a6-f379c3636a7e	ea144112-e023-43ed-841c-4577af9b2515	d43a4712-e5ba-4c0a-ac20-fa726bb93f49	\N
808c0ee4-17e6-4602-987b-466c8cbf69ee	a1f926ad-2187-47f4-abb8-fce671e5fd93	d43a4712-e5ba-4c0a-ac20-fa726bb93f49	\N
5be01b57-f4f2-4bf6-8aaa-bab4670a34e5	3999ec01-7f33-4dbd-9318-54b467a340ba	d43a4712-e5ba-4c0a-ac20-fa726bb93f49	\N
\.


--
-- TOC entry 2870 (class 0 OID 16486)
-- Dependencies: 201
-- Data for Name: customers; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.customers (customer_id, customer_name, pwd, balance, room, address, city, zipcode, us_state, is_admin) FROM stdin;
aa1526c5-8184-4751-af39-a89286dd3891	Vilma	$argon2d$v=19$m=65536,t=3,p=4$sBYW+828iLyLrVyb3/uKTQ$jQOhzYnNpJSwz9Pg2ObNLw	13.99	1001	10501 FGCU Blvd S	Fort Myers	33965	FL	f
bf30aaaa-a894-4cfd-bed3-08a9bb5fa9db	Chad	$argon2d$v=19$m=65536,t=3,p=4$sBYW+828iLyLrVyb3/uKTQ$jQOhzYnNpJSwz9Pg2ObNLw	15.99	1007	10501 FGCU Blvd S.	Fort Myers	33965	FL	t
60d02201-33d6-43e6-82bf-d43b59e75de3	Tyler	$argon2d$v=19$m=65536,t=3,p=4$sBYW+828iLyLrVyb3/uKTQ$jQOhzYnNpJSwz9Pg2ObNLw	33.00	1327	10501 FGCU Blvd S	Fort Myers	33965	FL	f
\.


--
-- TOC entry 2866 (class 0 OID 16419)
-- Dependencies: 197
-- Data for Name: genres; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.genres (genre_id, genre_name) FROM stdin;
56d865e5-5559-4400-9b2c-1bd12a2ded52	Comedy
ce589234-6f44-4f70-96d2-b10a2655facd	Horror
92ca9a80-2765-4575-94c4-9426e0062380	Action
59a23857-36eb-43f8-b983-bf47d8847dab	Animation
01303d6a-a851-4355-aceb-a9a96a0af913	Family
42b26f20-eb1a-4b40-9550-c22a6d08fdba	Romance
c3197809-e40d-4ba0-9c4f-ca43b6e7a6a7	Drama
28de25bc-a7cb-4f9c-b3ed-aa8f183162ac	Sports
32b7fa70-09a9-47d4-b5d5-8e9d9cee96e6	Science Fiction
8d3d1a8c-5c4a-4fcd-9831-0a20fba7a55f	Fiction
\.


--
-- TOC entry 2867 (class 0 OID 16424)
-- Dependencies: 198
-- Data for Name: movies; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.movies (movie_id, title, director, description, release_date, image, genre_id, price, viewed, last_modified) FROM stdin;
6159b6e4-3c17-4dd0-8923-174a5fc9a9e3	Captain America: Civil War	Joe Russo and Anthony Russo	Political interference in the Avengers' activities causes a rift between former allies Captain America and Iron Man.	2016-04-06	https://i.imgur.com/ZAlO2Eq.jpg	92ca9a80-2765-4575-94c4-9426e0062380	0.50	3	2018-10-15 23:29:06.327-04
b78281d4-8b0e-40d1-b5d6-d51f895d2962	Men in Black	Barry Sonnenfeld	A police officer joins a secret organization that polices and monitors extraterrestrial interactions on Earth.	1997-07-02	https://i.imgur.com/ev6eRey.jpg	92ca9a80-2765-4575-94c4-9426e0062380	0.00	3	2018-10-15 23:29:06.327-04
bf5acf5b-2f85-4056-aec1-24c6f57b4416	The Shawshank Redemption	Frank Darabont	Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.	1994-10-14	https://i.imgur.com/ldKX4wu.jpg	c3197809-e40d-4ba0-9c4f-ca43b6e7a6a7	0.00	3	2018-10-15 23:29:06.327-04
172e7dcb-583d-4b2e-9533-94684fe794b0	Magnificent Seven	Antoine Fuqua	Seven gun men in the old west gradually come together to help a poor village against savage thieves.	2016-09-23	https://i.imgur.com/CDTXtPS.jpg	92ca9a80-2765-4575-94c4-9426e0062380	1.00	2	2018-10-15 23:29:06.327-04
a2714e08-27a2-4805-b0c3-1a5e3b3933cd	Se7en	David Fincher	Two detectives, a rookie and a veteran, hunt a serial killer who uses the seven deadly sins as his modus operandi.	1995-09-22	https://i.imgur.com/SltvaDv.jpg	c3197809-e40d-4ba0-9c4f-ca43b6e7a6a7	0.00	2	2018-10-15 23:29:06.327-04
22c67054-30be-42c2-9bfb-4a56b28a578a	Rogue One: A Star Wars Story	Gareth Edwards	The Rebellion makes a risky move to steal the plans to the Death Star, setting up the epic saga to follow.	2016-12-16	https://i.imgur.com/fLgqSsR.jpg	32b7fa70-09a9-47d4-b5d5-8e9d9cee96e6	1.00	2	2018-10-15 23:29:06.327-04
f83eb6f1-d947-405a-97b1-bcf39f681b71	Snowden	Oliver Stone	The NSA's illegal surveillance techniques are leaked to the public by one of the agency's employees, Edward Snowden, in the form of thousands of classified documents distributed to the press.	2016-09-16	https://i.imgur.com/bWBzsjD.jpg	c3197809-e40d-4ba0-9c4f-ca43b6e7a6a7	0.00	3	2018-10-15 23:29:06.327-04
da9757e8-610e-43fb-95dc-3a59b93bdfd0	12 Angry Men	Sidney Lumet	A jury holdout attempts to prevent a miscarriage of justice by forcing his colleagues to reconsider the evidence.	1957-04-13	https://i.imgur.com/9iLXaRR.jpg	c3197809-e40d-4ba0-9c4f-ca43b6e7a6a7	0.00	1	2018-10-15 23:29:06.327-04
c66cd1c8-5e52-4a1e-a7dc-8788541fdd8b	Star Wars: Episode VII: The Force Awakens	J.J. Abrams	Three decades after the defeat of the Galactic Empire, a new threat arises. The First Order attempts to rule the galaxy and only a ragtag group of heroes can stop them, along with the help of the Resistance.	2015-12-18	https://i.imgur.com/sY6KAhj.jpg	32b7fa70-09a9-47d4-b5d5-8e9d9cee96e6	0.00	2	2018-10-15 23:29:06.327-04
20dbbcb7-6978-4792-bed7-4924ccb8b915	The Good, the Bad, and the Ugly	Sergio Leone	A bounty hunting scam joins two men in an uneasy alliance against a third in a race to find a fortune in gold buried in a remote cemetery.	1967-12-29	https://i.imgur.com/RbvCZ09.jpg	92ca9a80-2765-4575-94c4-9426e0062380	0.00	2	2018-10-15 23:29:06.327-04
d43a4712-e5ba-4c0a-ac20-fa726bb93f49	Spirited Away	Hayao Miyazaki	During her family's move to the suburbs, a sullen 10-year-old girl wanders into a world ruled by gods, witches, and spirits, and where humans are changed into beasts.	2003-03-28	https://i.imgur.com/gfdSlRZ.jpg	59a23857-36eb-43f8-b983-bf47d8847dab	0.00	6	2018-10-15 23:29:06.327-04
01d74d71-9dfb-4314-9d05-2f736a35f5c8	Star Wars: Episode VIII	Rian Johnson	Having taken her first steps into a larger world, Rey continues her epic journey with Finn, Poe, and Luke Skywalker in the next chapter of the saga.	2017-12-15	https://i.imgur.com/jmQenR4.jpg	32b7fa70-09a9-47d4-b5d5-8e9d9cee96e6	0.00	3	2018-10-15 23:29:06.327-04
7f5d368a-be44-4ac9-9408-fcf7903e99e9	The Godfather	Francis Ford Coppola	The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.	1972-05-24	https://i.imgur.com/a7wq5iQ.jpg	c3197809-e40d-4ba0-9c4f-ca43b6e7a6a7	0.00	1	2018-10-15 23:29:06.327-04
0759e092-c85f-4ac3-a501-fc2e42d82dd1	One Flew Over the Cuckoo's Nest	Milos Forman	A criminal pleads insanity after getting into trouble again and once in the mental institution rebels against the oppressive nurse and rallies up the scared patients.	1975-11-19	https://i.imgur.com/dT1bPzC.jpg	56d865e5-5559-4400-9b2c-1bd12a2ded52	0.00	3	2018-10-15 23:29:06.327-04
cb98c0ca-7ea2-4388-ba39-b92fe294bf7c	Forrest Gump	Robert Zemeckis	Forrest Gump, while not intelligent, has accidentally been present at many historic moments, but his true love, Jenny Curran, eludes him.	1994-07-06	https://i.imgur.com/VmO464D.jpg	56d865e5-5559-4400-9b2c-1bd12a2ded52	0.00	4	2018-10-15 23:29:06.327-04
fddf0e73-2aec-4de3-8d9c-e2ba8c8c1dae	The Godfather: Part II	Francis Ford Coppola	The early life and career of Vito Corleone in 1920s New York is portrayed while his son, Michael, expands and tightens his grip on his crime syndicate stretching from Lake Tahoe, Nevada to pre-revolution 1958 Cuba.	1974-12-20	https://i.imgur.com/BQkqHGb.jpg	c3197809-e40d-4ba0-9c4f-ca43b6e7a6a7	0.00	2	2018-10-15 23:29:06.327-04
\.


--
-- TOC entry 2872 (class 0 OID 16511)
-- Dependencies: 203
-- Data for Name: ratings; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.ratings (rating_id, customer_id, movie_id, score, last_modified) FROM stdin;
80720ab7-84ad-4e1f-9fa3-dfeffa16ba8b	60d02201-33d6-43e6-82bf-d43b59e75de3	cb98c0ca-7ea2-4388-ba39-b92fe294bf7c	5	2018-10-20 10:53:24.789215-04
644281e8-5591-4c0f-bc8f-8a21cef5a09e	bf30aaaa-a894-4cfd-bed3-08a9bb5fa9db	172e7dcb-583d-4b2e-9533-94684fe794b0	2	2018-10-20 10:55:48.326365-04
eb0f0ec2-5d93-4068-a3f7-bc8f89c1e721	bf30aaaa-a894-4cfd-bed3-08a9bb5fa9db	01d74d71-9dfb-4314-9d05-2f736a35f5c8	5	2018-10-20 11:03:13.009807-04
328409b8-1cb5-48fd-8136-23148d6f6943	bf30aaaa-a894-4cfd-bed3-08a9bb5fa9db	0759e092-c85f-4ac3-a501-fc2e42d82dd1	5	2018-10-20 11:13:22.183977-04
\.


--
-- TOC entry 2873 (class 0 OID 16527)
-- Dependencies: 204
-- Data for Name: rentals; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.rentals (rental_id, customer_id, movie_id, rental_date, cost) FROM stdin;
9a2fde7d-3d85-4d75-8aaa-23e2ebeda22f	60d02201-33d6-43e6-82bf-d43b59e75de3	cb98c0ca-7ea2-4388-ba39-b92fe294bf7c	2018-10-20	0
ff72cdad-7298-42b9-acd3-8ab70cddc47a	60d02201-33d6-43e6-82bf-d43b59e75de3	bf5acf5b-2f85-4056-aec1-24c6f57b4416	2018-10-20	0
845facb1-c12c-4b29-a43b-dc48f2b468ab	60d02201-33d6-43e6-82bf-d43b59e75de3	cb98c0ca-7ea2-4388-ba39-b92fe294bf7c	2018-10-20	0
8d13f349-dfb5-49ce-9eff-c24a425b1c2d	60d02201-33d6-43e6-82bf-d43b59e75de3	cb98c0ca-7ea2-4388-ba39-b92fe294bf7c	2018-10-20	0
7428c68a-366b-4aa6-a177-809ac0ffd9ba	bf30aaaa-a894-4cfd-bed3-08a9bb5fa9db	172e7dcb-583d-4b2e-9533-94684fe794b0	2018-10-20	1
20ce7a64-3d21-437b-b0ad-c3f17c6dada0	bf30aaaa-a894-4cfd-bed3-08a9bb5fa9db	0759e092-c85f-4ac3-a501-fc2e42d82dd1	2018-10-20	0
51ddd55e-21a6-4597-acc8-54215d00ad34	bf30aaaa-a894-4cfd-bed3-08a9bb5fa9db	01d74d71-9dfb-4314-9d05-2f736a35f5c8	2018-10-20	0
a7e1f3d8-48c3-45a6-b7cc-bdd0f1d94d65	bf30aaaa-a894-4cfd-bed3-08a9bb5fa9db	0759e092-c85f-4ac3-a501-fc2e42d82dd1	2018-10-20	0
\.


--
-- TOC entry 2871 (class 0 OID 16504)
-- Dependencies: 202
-- Data for Name: search_terms; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.search_terms (searchterm_id, term, freq) FROM stdin;
00e4dc29-8607-4637-9b56-ab10520557be	a	3
fd12c425-6bcb-41eb-940b-924c70c9982e	he	1
750ce8d1-4ab4-4559-9700-26e600082e63	js	1
\.


--
-- TOC entry 2725 (class 2606 OID 16454)
-- Name: actors actors_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.actors
    ADD CONSTRAINT actors_pkey PRIMARY KEY (actor_id);


--
-- TOC entry 2727 (class 2606 OID 16464)
-- Name: casting casting_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.casting
    ADD CONSTRAINT casting_pkey PRIMARY KEY (casting_id);


--
-- TOC entry 2729 (class 2606 OID 16492)
-- Name: customers customers_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customers
    ADD CONSTRAINT customers_pkey PRIMARY KEY (customer_id);


--
-- TOC entry 2721 (class 2606 OID 16423)
-- Name: genres genres_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.genres
    ADD CONSTRAINT genres_pkey PRIMARY KEY (genre_id);


--
-- TOC entry 2723 (class 2606 OID 16432)
-- Name: movies movies_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.movies
    ADD CONSTRAINT movies_pkey PRIMARY KEY (movie_id);


--
-- TOC entry 2735 (class 2606 OID 16516)
-- Name: ratings ratings_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ratings
    ADD CONSTRAINT ratings_pkey PRIMARY KEY (rating_id);


--
-- TOC entry 2737 (class 2606 OID 16532)
-- Name: rentals rentals_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rentals
    ADD CONSTRAINT rentals_pkey PRIMARY KEY (rental_id);


--
-- TOC entry 2731 (class 2606 OID 16562)
-- Name: search_terms search-term_uniq; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.search_terms
    ADD CONSTRAINT "search-term_uniq" UNIQUE (term);


--
-- TOC entry 2733 (class 2606 OID 16510)
-- Name: search_terms search_terms_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.search_terms
    ADD CONSTRAINT search_terms_pkey PRIMARY KEY (searchterm_id);


--
-- TOC entry 2739 (class 2606 OID 16476)
-- Name: casting actor_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.casting
    ADD CONSTRAINT actor_fkey FOREIGN KEY (actor_id) REFERENCES public.actors(actor_id);


--
-- TOC entry 2742 (class 2606 OID 16522)
-- Name: ratings customer_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ratings
    ADD CONSTRAINT customer_fkey FOREIGN KEY (customer_id) REFERENCES public.customers(customer_id);


--
-- TOC entry 2744 (class 2606 OID 16538)
-- Name: rentals customer_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rentals
    ADD CONSTRAINT customer_fkey FOREIGN KEY (customer_id) REFERENCES public.customers(customer_id);


--
-- TOC entry 2738 (class 2606 OID 16433)
-- Name: movies genre-id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.movies
    ADD CONSTRAINT "genre-id_fkey" FOREIGN KEY (genre_id) REFERENCES public.genres(genre_id);


--
-- TOC entry 2740 (class 2606 OID 16481)
-- Name: casting movie_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.casting
ADD CONSTRAINT movie_fkey FOREIGN KEY (movie_id) REFERENCES public.movies(movie_id);


--
-- TOC entry 2741 (class 2606 OID 16517)
-- Name: ratings movie_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ratings
ADD CONSTRAINT movie_fkey FOREIGN KEY (movie_id) REFERENCES public.movies(movie_id);


--
-- TOC entry 2743 (class 2606 OID 16533)
-- Name: rentals movie_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rentals
ADD CONSTRAINT movie_fkey FOREIGN KEY (movie_id) REFERENCES public.movies(movie_id);


-- Completed on 2018-10-20 19:59:18

--
-- PostgreSQL database dump complete
--

