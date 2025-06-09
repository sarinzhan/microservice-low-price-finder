use parser-db
db.createUser({
    user: "parser",
    pwd: "parser",
    roles: [{ role: "readWrite", db: "parser-db" }]
})

db.parser-db.webcite.insertMany(
    [
        {
            name: "SULPAK",
            urls: [
                "https://www.sulpak.kg/f/smartfoniy/bishkek/1056_477",
                "https://www.sulpak.kg/f/smartfoniy/bishkek/1056_62",
                "https://www.sulpak.kg/f/smartfoniy/bishkek/1056_1"
            ]
        },
        {
            name: "O_STORE",
            urls: [
                "https://ostore.kg/ru/phones/xiaomi/",
                "https://ostore.kg/ru/phones/apple/",
                "https://ostore.kg/ru/phones/samsung/"
            ]
        },
        {
            name: "MY_PHONE",
            urls: [
                "https://www.myphone.kg/ru/catalog/cell?sort%5Bby%5D=price_min&sort%5Bord%5D=desc&brn%5B%5D=77&price%5Bmin%5D=0&price%5Bmax%5D=0",
                "https://www.myphone.kg/ru/catalog/cell?sort%5Bby%5D=price_min&sort%5Bord%5D=desc&brn%5B%5D=5&price%5Bmin%5D=&price%5Bmax%5D=",
                "https://www.myphone.kg/ru/catalog/cell?sort%5Bby%5D=price_min&sort%5Bord%5D=desc&brn%5B%5D=21&price%5Bmin%5D=0&price%5Bmax%5D=0"
            ]
        }
    ]
)