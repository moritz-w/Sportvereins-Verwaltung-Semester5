
function executeScript() {
    /* DB connection config */
    var dbname = "sportsclub";
    var connection = "localhost:32768";     // default port: 27017
    var collections = ["Person", "Team", "Department", "Tournament"];

    /* Random data config */
    var personDataEntries = 50;
    var leagueDataEntries = 70;         // trying to estimate 7 leagues per sport (currently 10 sports)
    var teamDataEntries = 90;
    var tournamentDataEntries = 20;
    var participatingTeamsPerTournamentDataEntries = 8;
    var encountersPerTournamentDataEntries = 5;    // number of encounters per tournament
    var teamSize = 7;                   // persons per team
    // the number of departments and sports is limited by the elements in the random array
    var defaultYearMin = 1950;
    var defaultYearMax = 2000;

    /* Min inclusive, max exclusive */
    function getRandomNumber(min, max) {
        return Math.floor(Math.random() * (max - min) + min);
    }

    function getRandomDate(yearMin, yearMax) {
        return new Date(getRandomNumber(yearMin, yearMax) + "-" + getRandomNumber(1, 12) + "-" + getRandomNumber(1, 30));
    }

    var firstNames = [
        "Elizabeth", "Aimee", "Joseph", "Giacomo", "Guinevere", "Matthew", "Carter", "Yuli", "Quincy",
        "Tanisha", "Kareem", "Chase", "Keefe", "Edan", "Megan", "Rae", "Kitra", "Dolan", "Cain", "Shaine",
        "Tatiana", "Rylee", "Rachel", "Regan", "Isabella", "Orli", "Linus", "Wyatt", "Jacob", "Tucker",
        "Josiah", "Indira", "Brynne", "Hyacinth", "Nerea", "Cheyenne", "Thane", "Clark", "Hiroko", "Constance",
        "Elvis", "Kirby", "Solomon", "Liberty", "Hu", "Finn", "Jameson", "Dawn", "Summer", "Cain", "Alfons"
    ];

    var lastNames = [
        "Vinson", "Gibson", "Chase", "Mcknight", "Nolan", "Morales", "Jordan", "Lambert", "Emerson", "Hess",
        "Hardy", "Carr", "Kinney", "Webster", "Maldonado", "Farrell", "French", "Robles", "Colon", "Guerra",
        "Lawrence", "Schroeder", "Chen", "Martinez", "Hendrix", "Rich", "Mason", "Nieves", "Oliver", "Patel",
        "Leonard", "Morse", "Leonard", "Fuller", "Franks", "England", "Martin", "Olsen", "Dorsey", "Lopez",
        "Barrera", "Strong", "Hopkins", "Durham", "Hunt", "Stone", "Phelps", "Hampton", "Bender", "Burks"
    ];

    var genders = ["M", "F"];

    var streetNames = [
        "5725 Est St.", "P.O. Box 534, 8868 Adipiscing St.", "Ap #717-5533 Magna St.", "8892 Purus Ave",
        "Ap #493-6557 Vel Rd.", "P.O. Box 272, 4477 Neque Ave", "P.O. Box 234, 2975 Elit Avenue",
        "829-6148 Porttitor St.", "P.O. Box 579, 2653 Ipsum St.", "P.O. Box 695, 6746 Pede, Rd.",
        "814-7434 Urna. Road", "P.O. Box 974, 3867 A Avenue", "387-759 Pede St.", "342-504 Scelerisque Ave",
        "Ap #477-2521 Turpis. Rd.", "Ap #658-5146 Arcu. Road", "8222 Dolor Street", "6587 Mi Street",
        "P.O. Box 709, 2461 Vestibulum Rd.", "1709 Gravida St.", "816-5971 Dui. Av.", "1236 Enim St.",
        "5158 Congue Av.", "982 Pede. Street", "956-7785 Amet Street", "6484 At St.", "Ap #300-1631 Odio, Ave",
        "3256 Consequat Rd.", "P.O. Box 511, 7679 Mauris St.", "229 Ante Rd.", "Ap #237-602 Consequat Avenue",
        "P.O. Box 116, 5602 Mauris Street", "1071 Sed Avenue", "2611 Porttitor Rd.", "P.O. Box 388, 6709 Cum Av.",
        "Ap #141-5593 Gravida Road", "Ap #459-1185 Ligula. Ave", "884-3357 Quisque Road", "6092 At, St.",
        "P.O. Box 845, 1410 Id Avenue", "3691 Ligula St.", "551-8709 Fusce Av.", "P.O. Box 300, 8515 Nunc Ave",
        "247-5960 Ac Ave", "5138 Id, St.", "4575 Cursus Rd.", "956-9942 Congue. Road", "2693 Mattis. Rd.",
        "P.O. Box 690, 513 Adipiscing St.", "680-914 Natoque Road"
    ];

    var cities = [
        "Gold Coast", "Dhuy", "Glauchau", "Picture Butte", "Polatlı", "Vlimmeren", "Brive-la-Gaillarde", "Peñalolén",
        "Esslingen", "Dawson Creek", "Gloucester", "Petrolina", "Bierk Bierghes", "Felitto", "Olathe", "Vitrolles",
        "Ludlow", "Mont-de-Marsan", "Lachine", "Calera de Tango", "Dornbirn", "Wood Buffalo", "Bhilwara", "Rennes",
        "Montgomery", "Bella", "Raymond", "Divinópolis", "Reno", "Thines", "Upplands Väsby", "Villata", "Morvi", "Kirriemuir",
        "Siena", "Satriano di Lucania", "Nagarcoil", "Green Bay", "Holman", "Couillet", "Impe", "Opole", "Oristano", "Sterling Heights",
        "Ospedaletto Lodigiano", "Portigliola", "Segni", "Amsterdam", "Darwin", "Rennes"
    ];

    var zipCodes = [
        "9188", "3199", "27-533", "48932-264", "A5 5TV", "271858", "L0V 7B7", "15734", "00100", "70608", "9556", "69556", "21914",
        "3484", "12306", "50815", "968119", "63970", "E4 8SU", "85766", "363771", "13889", "R4 4ZM", "931983", "X99 2KE", "74650",
        "421396", "5822", "6934", "83909", "1973", "81146-894", "60914", "99905", "10218", "71105", "P0R 3R3", "57837", "12899",
        "54032", "32075", "N1 7HD", "4368", "71904", "666384", "33150-478", "31136", "52669", "503704", "267731"
    ];

    var phoneNumbers = [
        "(030506) 190656", "(07185) 6607147", "(045) 67743211", "(066) 36992555", "(01811) 6227043", "(032632) 231722",
        "(0106) 99332757", "(072) 64278128", "(092) 42887387", "(030155) 894344", "(037983) 678846", "(0043) 20733987",
        "(072) 17381390", "(0614) 86268756", "(08151) 4565309", "(0297) 64759226", "(039423) 119134", "(0216) 30375770",
        "(0597) 90148141", "(015) 22512345", "(0480) 12383534", "(039137) 276183", "(033021) 608058", "(09019) 6072732",
        "(079) 31372697", "(008) 92962116", "(026) 70235529", "(028) 48040489", "(032668) 352362", "(097) 89919533",
        "(037) 72562874", "(060) 01042703", "(092) 65951791", "(032342) 438535", "(0930) 16870853", "(039497) 983940",
        "(0152) 32933395", "(094) 18734647", "(037969) 619804", "(0530) 56681661", "(054) 98866134", "(039576) 478351",
        "(0888) 67027105", "(0584) 49144057", "(020) 27964357", "(097) 89100796", "(039) 11091452", "(030257) 845395",
        "(0295) 53823445", "(050) 33667378"
    ];

    var emailAddresses = [
        "Anne.Kulas@rodolfo.io", "Olin_McClure@sherwood.co.uk", "Kolby_DAmore@liliane.io", "Merlin@gabriel.biz",
        "Mallory_Price@makenzie.biz", "Camren@brennon.tv", "Nelson@verona.tv", "Shanie_Kuvalis@christiana.biz",
        "Abbigail@randal.biz", "Vida@sid.net", "Lucinda@constantin.org", "Victor@toni.net", "Layla@annabell.biz",
        "Sean.Heidenreich@rosalind.co.uk", "Jake@guiseppe.ca", "Domenica.Farrell@norwood.name", "Kyle@zetta.net",
        "Shaun@samir.co.uk", "Reese_Rice@terrill.net", "Emilie_Mraz@bud.biz", "Fanny@magnus.biz", "Jody_Brekke@kaylin.name",
        "Jon@dorothy.info", "Rae@jarret.tv", "Yasmine.Kassulke@cordia.ca", "Jayden@jeanne.tv", "Marshall_Johnson@rusty.com",
        "Ceasar.Hoeger@junius.net", "Gay@letitia.biz", "Rashad@velda.us", "Rudy@vincenzo.com", "Ollie.Reynolds@muriel.biz",
        "Chester_Fritsch@elmer.com", "Katrine@mohammad.biz", "Jessyca.McCullough@chesley.net", "Ramiro.Schoen@shawn.us",
        "Samson.Schroeder@eldred.org", "Rosie_Jones@adeline.us", "Layne.Runte@eliza.name", "Madonna_Hane@austyn.info",
        "Asia@coty.net", "Orrin_Volkman@dangelo.io", "Lilliana@kayli.biz", "Deja@ethel.biz", "Elva_Pfannerstill@laverna.us",
        "Carleton@mackenzie.net", "Sally_Osinski@sylvester.name", "Susana.Padberg@ali.net", "Dock@dewitt.us", "Ubaldo_Green@suzanne.com"
    ];

    var teamsPrefix = [
        "Dornbirn", "Zuerich", "Chicago", "Compton", "New York", "Los Angeles", "Alberschwende", "Berlin", "Amsterdam", "Dublin",
        "Moscow", "Orechowo-Sujewo", "Peking", "Athens", "London", "Mexico City", "St George's", "La Paz", "El Paso", "Albuquerque",
        "Santa Fe", "Kingston", "Panama City", "Bogota", "Cali", "Medellin", "Porto", "Oslo", "Nuuk"
    ];

    var teamsMidname = [
        "Wolfs", "Bulls", "Tigers", "Giants", "Eagles", "Bulldogs", "Vikings", "Bears", "Jets", "Seahawks", "Steelers", "Falcons",
        "Chiefs", "Panthers", "House Cats", "Lakers", "Heat", "Warriors", "Wizards", "Badgers", "Masters", "Highlanders", "Sharks",
        "Smokers", "Kings", "Crows"
    ];

    var teamsPostfix = [
        "United", "Reloaded", "University Team", "Dynamic", "Youth Team", "Senior Team", "International", "Union", "National"
    ];

    var leaguePostfix = [
        "Champions League", "City League", "Youth League", "Senior League", "Just-not-good-enough League", "Premier League", "Master League",
        "Wanksters", "Newbie League", "Kings League", "Noobs League"
    ];

    /* Make sure that sports array has always more elements then the department array */
    var sports = [
        "Baseball", "Basketball", "Football", "Soccer", "Rugby", "Handball", "Volleyball", "Rowing", "Beerpong", "Fantasy Football"
    ];

    var deptNames = [
        "Master Department", "Lazy Fuckers Department", "Management Department", "Green Department", "Business Monkey Department"
    ];

    var tournamentPostfix = [
        "City Tournament", "Yearly Tournament", "Beerpong Championship", "Random Tournament", "Wintersports Tournament", "Just an other Tournament",
        "Royale Tournament", "Plastic trophy Tournament", "ESL Tournament"
    ];

    function composeTeamName() {
        return teamsPrefix[getRandomNumber(0, teamsPrefix.length)] + " "
            + teamsMidname[getRandomNumber(0, teamsMidname.length)] + " "
            + teamsPostfix[getRandomNumber(0, teamsPostfix.length)];
    }

    function composeLeagueName() {
        return teamsPrefix[getRandomNumber(0, teamsPrefix.length)] + " "
            + leaguePostfix[getRandomNumber(0, leaguePostfix.length)];
    }

    function composeTournamentName() {
        return teamsPrefix[getRandomNumber(0, teamsPrefix.length)]
            + " " + tournamentPostfix[getRandomNumber(0, tournamentPostfix.length)];
    }

    function getRandomizedArraySlice(pool, maxlength) {
        var slice = [];
        for (var index = 0; index < getRandomNumber(maxlength - 1, maxlength + 1); index++) {
            slice.push(pool[getRandomNumber(0, pool.length)]);
        }
        return slice;
    }

    var personIdPool = [];
    var leagueIdPool = [];
    var sportsIdPool = [];
    var teamIdPool = [];
    var participatingTeamsIndex = [];

    var exampleRoleId = new ObjectId();
    var adminRoleId = new ObjectId();

    function transformIdArrayToDbRef(ids, ref) {
        var dbRefs = [];
        ids.forEach(function (id, index, theArray) {
            dbRefs.push({
                "$ref": ref,
                "$id": id
            })
        });
        return dbRefs;
    }

    function generateAdminRole() {
        return {
            _id: adminRoleId,
            name: "Admin",
            privileges: [
                {
                    domain: "Person",
                    accessLevels: [
                        "read", "write"
                    ]
                },
                {
                    domain: "Team",
                    accessLevels: [
                        "read", "write"
                    ]
                },
                {
                    domain: "Department",
                    accessLevels: [
                        "read", "write"
                    ]
                },
                {
                    domain: "Tournament",
                    accessLevels: [
                        "read", "write", "special"
                    ]
                }
            ]
        }
    }

    function generateExampleRole() {
        return {
            _id: exampleRoleId,
            name: "Member",
            privileges: [
                {
                    domain: "Person",
                    accessLevels: [
                        "read"
                    ]
                },
                {
                    domain: "Tournament",
                    accessLevels: [
                        "read"
                    ]
                }
            ]
        };
    }

    function generateRandomizedPerson() {
        var uid = new ObjectId();
        personIdPool.push(uid);
        return {
            _id: uid,
            firstName: firstNames[getRandomNumber(0, firstNames.length)],
            lastName: lastNames[getRandomNumber(0, lastNames.length)],
            dateOfBirth: getRandomDate(defaultYearMin, defaultYearMax),
            gender: genders[getRandomNumber(0, 1)],
            address: {
                street: streetNames[getRandomNumber(0, streetNames.length)],
                zipCode: zipCodes[getRandomNumber(0, zipCodes.length)],
                city: cities[getRandomNumber(0, cities.length)]
            },
            contact: {
                phoneNumber: phoneNumbers[getRandomNumber(0, phoneNumbers.length)],
                emailAddress: emailAddresses[getRandomNumber(0, emailAddresses.length)]
            },
            sports: [sportsIdPool[getRandomNumber(0, sportsIdPool.length)]],
            roles: [
                {
                    "$ref": "Role",
                    "$id": exampleRoleId
                }
            ]
        };
    }

    function generateRandomizedLeague() {
        var uid = new ObjectId();
        leagueIdPool.push(uid);
        return {
            _id: uid,
            name: composeLeagueName()
        }
    }


    function generateSports(leagueArray) {
        var generatedSports = [];
        sports.forEach(function (sport)  {
            var uid = new ObjectId();
            sportsIdPool.push(uid);
            generatedSports.push(
            {
                _id: uid,
                name: sport,
                leagues: getRandomizedArraySlice(leagueArray, Math.floor(leagueDataEntries / sports.length))
            }
        );
    })
        ;
        return generatedSports;
    }


    function generateDepartments(sportArray) {
        var departments = [];
        var sliceSize = Math.floor(sports.length / deptNames.length);
        var i = 0;
        deptNames.forEach(function (deptname) {
            departments.push(
            {
                deptName: deptname,
                deptLeader: {
                    "$ref": "Person",
                    "$id": personIdPool[getRandomNumber(0, personIdPool.length)]
                },
                sports: sportArray.slice(i, i + sliceSize)
            }
        );
        i += sliceSize;
    });
        return departments;
    }

    function generateRandomizedTeam() {
        var uid = new ObjectId();
        teamIdPool.push(uid);
        var sliceIndex = getRandomNumber(0, personIdPool.length - teamSize);
        return {
            _id: uid,
            name: composeTeamName(),
            //transformIdArrayToDbRef(personIdPool.slice(sliceIndex, sliceIndex + teamSize + 1), "Person"),
            members: personIdPool.slice(sliceIndex, sliceIndex + teamSize + 1),
            trainers: [
                personIdPool[getRandomNumber(0, personIdPool.length)]
/*                {
                    "$ref": "Person",
                    "$id": personIdPool[getRandomNumber(0, personIdPool.length)]
                }*/
            ],
            league: leagueIdPool[getRandomNumber(0, leagueIdPool.length)],
            type: "intern"
        };
    }

    function generateRandomizedEncounter() {
        return {
            date: new Date("2018-01-01"),
            time: getRandomNumber(0, 1439),
            homeTeam: participatingTeamsIndex[getRandomNumber(Math.floor(participatingTeamsIndex.length / 2), participatingTeamsIndex.length)],
            guestTeam: participatingTeamsIndex[getRandomNumber(0, Math.floor(participatingTeamsIndex.length / 2))],
            guestTeamPoints: 0,
            homeTeamPoints: 0
        }
    }

    function generateRandomizedParticipant() {
        var teamIdIndex = getRandomNumber(0, teamIdPool.length);
        var participantId = new ObjectId();
        participatingTeamsIndex.push(participantId);
        return {
            _id: participantId,
            team: teamIdPool[teamIdIndex],
            teamName: "",
            participants: []
        }
    }

    function generateRandomizedTournament(encounters, participants) {
        return {
            name: composeTournamentName(),
            date: new Date("2019-01-01"),
            leagueName: "",
            sportsName: "",
            league: leagueIdPool[getRandomNumber(0, leagueIdPool.length)],
            encounters: encounters,
            teams: participants
        };
    }


    function insertRandomizedData() {

        var embeddedLeagueArray = [];
        for (var index = 0; index < leagueDataEntries; index++) {
            embeddedLeagueArray.push(generateRandomizedLeague());
        }
        db.Role.insertOne(generateExampleRole());
        db.Role.insertOne(generateAdminRole());
        var embeddedSportArray = generateSports(embeddedLeagueArray);
        for (index = 0; index < personDataEntries; index++) {
            db.Person.insertOne(generateRandomizedPerson());
        }
        db.Department.insertMany(generateDepartments(embeddedSportArray));
        for (index = 0; index < teamDataEntries; index++) {
            db.Team.insertOne(generateRandomizedTeam());
        }
/*

        for (index = 0; index < tournamentDataEntries; index++) {
            var embeddedParticipantsArray = [];
            for (var a = 0; a < participatingTeamsPerTournamentDataEntries; a++) {
                embeddedParticipantsArray.push(generateRandomizedParticipant());
            }
            var embeddedEncountersArray = [];
            for (var b = 0; b < encountersPerTournamentDataEntries; b++) {
                embeddedEncountersArray.push(generateRandomizedEncounter());
            }
            db.Tournament.insertOne(generateRandomizedTournament(embeddedEncountersArray, embeddedParticipantsArray));
        }
*/

    }

    var dbconnection = new Mongo();
    var db = dbconnection.getDB(dbname);

    collections.forEach(function (element) {
        db.getCollection(element).drop();
    });

    var snoopId = new ObjectId();
    var billyId = new ObjectId();

    db.Person.insertMany([
        {
            _id: snoopId,
            firstName: "Snoop",
            lastName: "Dogg",
            dateOfBirth: new Date("1990-01-02"),
            gender: "M",
            address: {
                street: "Dogg Street 187",
                zipCode: "D066",
                city: "Compton"
            },
            contact: {
                phoneNumber: "+43 11111 1111",
                emailAddress: "snoop@do.gg"
            },
            sports: [],
            roles: [
                {
                    "$ref": "Role",
                    "$id": adminRoleId
                }
            ]
        },
        {
            firstName: "TF",
            lastName: "Test",
            dateOfBirth: new Date("1990-01-02"),
            gender: "M",
            address: {
                street: "Dogg Street 187",
                zipCode: "D066",
                city: "Compton"
            },
            contact: {
                phoneNumber: "+43 11111 1111",
                emailAddress: "tf-test"
            },
            sports: [],
            roles: [
                {
                    "$ref": "Role",
                    "$id": adminRoleId
                }
            ]
        },
        {
            firstName: "Dr",
            lastName: "Dre",
            dateOfBirth: new Date("1990-01-02"),
            gender: "M",
            address: {
                street: "Dre Street 187",
                zipCode: "D066",
                city: "Compton"
            },
            contact: {
                phoneNumber: "+43 11111 1111",
                emailAddress: "studio@dre.dr"
            },
            sports: [],
            roles: [
                {
                    "$ref": "Role",
                    "$id": exampleRoleId
                }
            ]
        },
        {
            _id: billyId,
            firstName: "Billy",
            lastName: "Test",
            dateOfBirth: new Date("1990-01-02"),
            gender: "M",
            address: {
                street: "Dogg Street 187",
                zipCode: "D066",
                city: "Compton"
            },
            contact: {
                phoneNumber: "+43 11111 1111",
                emailAddress: "billy@test.at"
            },
            sports: [],
            roles: [
                {
                    "$ref": "Role",
                    "$id": adminRoleId
                }
            ]
        }

    ]);

    insertRandomizedData();

    var sliceIndex = getRandomNumber(0, personIdPool.length - teamSize);
    var snoopTeamName = composeTeamName();
    var dreTeamName = composeTeamName();
    var eazyETeamName = composeTeamName();
    var snoopDepartment = new ObjectId();
    var snoopLeague = new ObjectId();
    var snoopSport = new ObjectId();
    var snoopTeam = new ObjectId();
    var eazyETeam = new ObjectId();
    var dreTeam = new ObjectId();

    db.Team.insertMany([
        {
            _id: snoopTeam,
            name: snoopTeamName,
            members: personIdPool.slice(sliceIndex, sliceIndex + teamSize + 1),//transformIdArrayToDbRef(personIdPool.slice(sliceIndex, sliceIndex + teamSize + 1), "Person"),
            trainers: [ snoopId
                /*{
                    "$ref": "Person",
                    "$id": snoopId
                }*/
            ],
            league: snoopLeague,
            type: "Intern"
        },
        {
            _id: dreTeam,
            name: dreTeamName,
            members: personIdPool.slice(sliceIndex, sliceIndex + teamSize + 1),//transformIdArrayToDbRef(personIdPool.slice(sliceIndex, sliceIndex + teamSize + 1), "Person"),
            trainers: [ billyId
                /*{
                    "$ref": "Person",
                    "$id": snoopId
                }*/
            ],
            league: snoopLeague,
            type: "Intern"
        },
        {
            _id: eazyETeam,
            name: eazyETeamName,
            members: personIdPool.slice(sliceIndex, sliceIndex + teamSize + 1),//transformIdArrayToDbRef(personIdPool.slice(sliceIndex, sliceIndex + teamSize + 1), "Person"),
            trainers: [ billyId
                /*{
                    "$ref": "Person",
                    "$id": snoopId
                }*/
            ],
            league: snoopLeague,
            type: "Intern"
        },
        {
            _id: new ObjectId(),
            name: composeTeamName(),
            members: personIdPool.slice(sliceIndex, sliceIndex + teamSize + 1),//transformIdArrayToDbRef(personIdPool.slice(sliceIndex, sliceIndex + teamSize + 1), "Person"),
            trainers: [ snoopId
                /*{
                    "$ref": "Person",
                    "$id": snoopId
                }*/
            ],
            league: snoopLeague,
            type: "Intern"
        },
        {
            _id: new ObjectId(),
            name: composeTeamName(),
            members: personIdPool.slice(sliceIndex, sliceIndex + teamSize + 1),//transformIdArrayToDbRef(personIdPool.slice(sliceIndex, sliceIndex + teamSize + 1), "Person"),
            trainers: [ snoopId
                /*{
                    "$ref": "Person",
                    "$id": snoopId
                }*/
            ],
            league: snoopLeague,
            type: "Intern"
        }
    ]);

    db.Department.insertOne(
        {
            _id: snoopDepartment,
            deptName: "Snoop Dogg Department",
            deptLeader: {
                "$ref": "Person",
                "$id": snoopId
            },
            sports: [
                {
                    _id: snoopSport,
                    name: "Sheep Rodeo",
                    leagues: [
                        {
                            _id: snoopLeague,
                            name: "Sheep Classics"
                        }
                    ]
                }
            ]
        }
    );

    db.Tournament.insertOne(
        {
            name: composeTournamentName(),
            league: snoopLeague,
            sport: snoopSport,
            leagueName: "Dogg League",
            sportsName: "Football",
            date: new Date("2019-01-01"),
            encounters: [],
            teams: [
                {
                    _id: new ObjectId(),
                    teamName: snoopTeamName,
                    type: "Intern",
                    team: snoopTeam,
                    participants: []
                },
                {
                    _id: new ObjectId(),
                    teamName: dreTeamName,
                    type: "Intern",
                    team: dreTeam,
                    participants: []
                },
                {
                    _id: new ObjectId(),
                    teamName: eazyETeamName,
                    type: "Intern",
                    team: eazyETeam,
                    participants: []
                }
            ]
        }
    );

    // ----------------
    //
    // ----------------

    function insertData() {

    }
}
