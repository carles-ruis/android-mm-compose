package com.carles.kotlin.api

val POI_LIST_RESPONSE = """
{
   "list":[
      {
         "id":"1",
         "title":"Casa Batlló",
         "geocoordinates":"41.391926,2.165208"
      },
      {
         "id":"2",
         "title":"Fundació Antoni Tàpies",
         "geocoordinates":"41.39154,2.163835"
      },
      {
         "id":"3",
         "title":"Hospital de Sant Pau",
         "geocoordinates":"41.674202,2.314628"
      },
      {
         "id":"4",
         "title":"La pedrera - Casa Milà",
         "geocoordinates":"41.39558,2.162075"
      },
      {
         "id":"5",
         "title":"Fundació Suñol",
         "geocoordinates":"41.395173,2.16179"
      },
      {
         "id":"6",
         "title":"Museu Egipci -Fundació Clos",
         "geocoordinates":"41.394115,2.164693"
      },
      {
         "id":"7",
         "title":"Museu del Modersnime",
         "geocoordinates":"41.391749,2.163405"
      },
      {
         "id":"8",
         "title":"Museu del Rock",
         "geocoordinates":"41.394325,2.139587"
      },
      {
         "id":"9",
         "title":"Sagrada Família ",
         "geocoordinates":"41.403692,2.174006"
      },
      {
         "id":"10",
         "title":"Fundació ALORDA-DERKSEN",
         "geocoordinates":"41.398703,2.16877"
      },
      {
         "id":"11",
         "title":"Catedral",
         "geocoordinates":"41.406557,2.172375"
      },
      {
         "id":"12",
         "title":"Centre cultura contemporanea",
         "geocoordinates":"41.384601,2.16701"
      },
      {
         "id":"13",
         "title":"L'Aquarium",
         "geocoordinates":"41.383828,2.188253"
      },
      {
         "id":"14",
         "title":"Las Golondrinas (port)",
         "geocoordinates":"41.383828,2.188253"
      },
      {
         "id":"15",
         "title":"Liceu",
         "geocoordinates":"41.388916,2.166023"
      },
      {
         "id":"16",
         "title":"Mirador de Colón",
         "geocoordinates":"41.382412,2.177954"
      },
      {
         "id":"17",
         "title":"Museu D'art Contemporani",
         "geocoordinates":"41.383329,2.167397"
      },
      {
         "id":"18",
         "title":"Museus de la moto",
         "geocoordinates":"41.646236,2.429352"
      },
      {
         "id":"19",
         "title":"Museu de la cera",
         "geocoordinates":"41.381188,2.176967"
      },
      {
         "id":"20",
         "title":"Museu de la xocolata",
         "geocoordinates":"41.390397,2.182031"
      },
      {
         "id":"21",
         "title":"Museu de l'erotica",
         "geocoordinates":"41.382798,2.172546"
      },
      {
         "id":"22",
         "title":"Museu d'història de Catalunya",
         "geocoordinates":"41.385149,2.186451"
      },
      {
         "id":"23",
         "title":"Museu d'història de la ciutat",
         "geocoordinates":"41.387596,2.177567"
      },
      {
         "id":"24",
         "title":"Museu d'idees i invencions",
         "geocoordinates":"41.382508,2.178082"
      },
      {
         "id":"25",
         "title":"Museu Maritim",
         "geocoordinates":"41.380641,2.174478"
      },
      {
         "id":"26",
         "title":"Museu Picasso",
         "geocoordinates":"41.386903,2.177696"
      },
      {
         "id":"27",
         "title":"Palau de la música",
         "geocoordinates":"41.388079,2.175636"
      },
      {
         "id":"28",
         "title":"Palau Güell",
         "geocoordinates":"41.382734,2.174349"
      },
      {
         "id":"29",
         "title":"Santa Maria del Mar",
         "geocoordinates":"41.383812,2.181966"
      },
      {
         "id":"30",
         "title":"Fundació de les arts i els artistes - Museu europeu d'art modern \"meam\"",
         "geocoordinates":"41.388433,2.1804"
      },
      {
         "id":"31",
         "title":"Zoo",
         "geocoordinates":"41.391105,2.184262"
      },
      {
         "id":"32",
         "title":"CaixaForum",
         "geocoordinates":"41.37668,2.162933"
      },
      {
         "id":"33",
         "title":"Castell de Montjuic",
         "geocoordinates":"41.367952,2.167096"
      },
      {
         "id":"34",
         "title":"Fònt Màgica",
         "geocoordinates":"41.374587,2.151775"
      },
      {
         "id":"35",
         "title":"Fundació Joan Miró",
         "geocoordinates":"41.371624,2.160101"
      },
      {
         "id":"36",
         "title":"Museu Òlimpic i de l'esport Joan Antoni Samaranch",
         "geocoordinates":"41.368371,2.157011"
      },
      {
         "id":"37",
         "title":"Museu nacional d'art de Catalunya",
         "geocoordinates":"41.368145,2.154071"
      },
      {
         "id":"38",
         "title":"Pavelló Mies Van Der Rohe",
         "geocoordinates":"41.372139,2.147827"
      },
      {
         "id":"39",
         "title":"Poble Espanyol",
         "geocoordinates":"41.373685,2.158127"
      },
      {
         "id":"40",
         "title":"Telefèric de Montjuic",
         "geocoordinates":"41.374136,2.170529"
      },
      {
         "id":"41",
         "title":"Transbordador aeri",
         "geocoordinates":"41.372236,2.181687"
      },
      {
         "id":"42",
         "title":"Parc Güell",
         "geocoordinates":"41.415247,2.165508"
      },
      {
         "id":"43",
         "title":"Casa museu Gaudi (Parc Güell)",
         "geocoordinates":"41.414185,2.152848"
      },
      {
         "id":"44",
         "title":"Parc d'atraccions Tibidabo",
         "geocoordinates":"41.422118,2.120168"
      },
      {
         "id":"45",
         "title":"Cosmocaixa",
         "geocoordinates":"41.416728,2.131519"
      },
      {
         "id":"46",
         "title":"Museu Blau",
         "geocoordinates":"41.412302,2.220354"
      },
      {
         "id":"47",
         "title":"Monestir de Pedralbes",
         "geocoordinates":"41.402308,2.112808"
      },
      {
         "id":"48",
         "title":"Museu de la ceramica, arts decoratives i tèxtil",
         "geocoordinates":"41.388449,2.119997"
      },
      {
         "id":"49",
         "title":"Museu de la Música",
         "geocoordinates":"41.425481,2.181816"
      },
      {
         "id":"50",
         "title":"Museu F.C Barcelona",
         "geocoordinates":"41.381864,2.122829"
      },
      {
         "id":"51",
         "title":"Torre de Collserola",
         "geocoordinates":"41.418401,2.114053"
      },
      {
         "id":"52",
         "title":"Il·luminació Torre Agbar",
         "geocoordinates":"41.40358,2.189434"
      }
   ]
}"""

val POI_DETAIL_1_RESPONSE = """
    {
       "id":"1",
       "title":"Casa Batlló",
       "address":"Paseo de Gracia, 43, 08007 Barcelona",
       "transport":"Underground:Passeig de Gràcia -L3",
       "email":"http://www.casabatllo.es/en/",
       "geocoordinates":"41.391926,2.165208",
       "description":"Casa Batlló is a key feature in the architecture of modernist Barcelona. It was built by Antoni Gaudí between 1904 and 1906 having been commissioned by the textile industrialist Josep Batlló. Nowadays, the spectacular facade is an iconic landmark in the city. The \"Manzana de la Discordia\", or Block of Discord, is a series of buildings in Passeig de Gràcia. It is home to a collection of works by the most renowned architects, amongst which is Casa Batlló. The house, now a museum, is open to the public, both for cultural visits and for celebrating events in its splendid modernist function rooms.",
       "phone":"info@casabatllo.cat"
    }
"""

val POI_DETAIL_52_RESPONSE = """
    {
       "id":"52",
       "title":"Il·luminació Torre Agbar",
       "address":"Avinguda Diagonal, s/n. Barcelona (Poble Nou) España ",
       "transport":"null",
       "email":"null",
       "geocoordinates":"41.40358,2.189434",
       "description":"One of the most characteristic elements of the building is lit up at night. The tower has more than 4,500 luminous devices that can operate independently using LED technology and enables the generation of luminous images in the entire facade. The system allows the project to 16 million colors, thanks to a sophisticated system of hardware and software, plus the ability to create color transitions are also independent, showing no effect delays and creating energy efficiency impactante. tecnoligía this assumes that the cost of having fully illuminated for one hour is equivalent to about 6 euros.The unique building lighting system, dubbed by its creator as Yann Kersalé Diffraction and defined itself as \"an airy cloud of color that looks moire\", 14 can be enjoyed daily for several horas and is also often used in the celebration of various events, such as holding the appointment of Barcelona as the capital of the Union for the Mediterranean,the 50 anniversary of the Treaty of Roma or autismo18 day commemoration among others.",
       "phone":"Tel: +3493 342 20 00"
    }
"""