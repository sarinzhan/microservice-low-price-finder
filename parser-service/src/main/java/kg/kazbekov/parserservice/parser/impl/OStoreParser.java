package kg.kazbekov.parserservice.parser.impl;

import com.microsoft.playwright.*;
import kg.kazbekov.parserservice.parser.Parser;
import kg.kazbekov.parserservice.parser.ParsedCardDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Qualifier("OStoreParser")
@RequiredArgsConstructor
@Slf4j
public class OStoreParser implements Parser {
    private final Browser browser;
    @Override
    public List<ParsedCardDto> parseManufactureCatalog(String url) {
        log.debug("Start parsing o store. Url: {}", url);
        List<ParsedCardDto> productRespons = new ArrayList<>();
        try(Page page = browser.newPage()) {
            page.navigate(url);
            do {
                var document = Jsoup.parse(page.content());
                var elements = document.select("div.productList div.tabloid");

                for (var element : elements) {
                    try {
                        var productResponse = parseCart(element);
                        productRespons.add(productResponse);
                    } catch (Exception ex) {
                        log.error("Error while parsing o store cart.\nEx: {}.\nHtml:\n {}", ex, element.html());
                    }
                }

                var nextButton = page.querySelector("li.bx-pag-next a");
                if (nextButton == null) {
                    break;
                }
                nextButton.click();

            } while (true);
        }
        log.debug("Parsed {} product from o store.", productRespons.size());
        return productRespons;
    }



//    <div class="tabloid">
//                    <a href="#" class="removeFromWishlist" data-id="9427"></a><div class="markerContainer"><div class="marker" style="background-color: #424242">Рассрочка 0-0-12</div><div class="marker" style="background-color: #03001c">+ 20ГБ интернета</div><div class="marker" style="background-color: #f61f7b">+ ПОДАРОК</div><div class="marker" style="background-color: #00ed28">Зарегистрирован в ГСИ</div></div><div class="rating"><i class="m" style="width:0%"></i><i class="h"></i></div>
////  1)   ссылка
////  <a href="/ru/product/apple-iphone-16-pro-max-1tb/" class="picture"><img src="https://ostore.kg/upload/iblock/764/764d40a8ccfc52af0bd7a286035833f8.png" alt="Apple iPhone 16 Pro Max 1TB"><span class="getFastView" data-id="9425">Быстрый просмотр</span></a>

////  2) производитель, модель
////   <a href="/ru/product/apple-iphone-16-pro-max-1tb/" class="name"><span class="middle">Apple iPhone 16 Pro Max 1TB</span></a>

//// 3) цена
////  <a class="price">159900 <span class="kgs">c</span></a>
//
// <a href="/ru/product/apple-iphone-16-pro-max-1tb/" class="aydBtn detailBtn">Посмотреть</a>
//<!-- Aydin быстрая покупка -->
//<a href="#" class="fastBack aydAddCart changeID" data-id="9425"><img src="/bitrix/templates/dresscode/images/incart.png" alt="" class="icon">Заказать</a>
//<div class="optional">
//<div class="row">
//<!-- Aydin кнопка корзина -->
//<a href="#" class="addCart label changeID changeCart" data-id="9425" onmousedown="try { rrApi.addToBasket(9425) } catch(e) {}"><img src="/bitrix/templates/dresscode/images/fastBack.png" alt="" class="icon">В корзину</a>
//<a href="#" class="addCompare label" data-id="9425"><img src="/bitrix/templates/dresscode/images/compare.png" alt="" class="icon">К сравнению</a>
//</div>
//<div class="row">
//<a href="#" class="addWishlist label" data-id="9427"><img src="/bitrix/templates/dresscode/images/wishlist.png" alt="" class="icon">В избранное</a>
//// в наличий
////  <span class="inStock label changeAvailable"><img src="/bitrix/templates/dresscode/images/inStock.png" alt="В наличии" class="icon"><span>В наличии</span>

//    </span></div></div><div class="skuProperty" data-name="COLOR" data-level="1" data-highload="N"><div class="skuPropertyName">Цвет</div><ul class="skuPropertyList"><li class="skuPropertyValue selected" data-name="COLOR" data-value="Gold"><a href="#" class="skuPropertyLink">Gold</a></li><li class="skuPropertyValue" data-name="COLOR" data-value="Black"><a href="#" class="skuPropertyLink">Black</a></li></ul></div>
//    <div class="skuProperty" data-name="MEMORY" data-level="2" data-highload="N"><div class="skuPropertyName">Память</div><ul class="skuPropertyList">

//// 4) оперативная и встроенная память
////  <li class="skuPropertyValue selected" data-name="MEMORY" data-value="8GB | 1TB"><a href="#" class="skuPropertyLink">8GB | 1TB</a></li></ul>
//  </div>


    public ParsedCardDto parseCart(Element element) {
            ParsedCardDto parsedCardDto = new ParsedCardDto();
            // parse url
            String url = "https://ostore.kg" + element.select("a.picture").attr("href");
            parsedCardDto.setUrl(url);

            // parse ram and storage
            var ramAndStorage = element.select("li.skuPropertyValue[data-name=MEMORY]").text();
            var ramAndStorageArray = ramAndStorage.split(" ");
            var ram = ramAndStorageArray[0];
            var storage = ramAndStorageArray[2];
            parsedCardDto.setRam(
                    Integer.parseInt(ram.replaceAll("\\D+", ""))
            );
            parsedCardDto.setStorage(
                    Integer.parseInt(storage.replaceAll("\\D+", ""))
            );
            if(parsedCardDto.getStorage() < 10){
                parsedCardDto.setStorage(parsedCardDto.getStorage() * 1024);
            }

            // parse manufacture and model
            var manufactureAndModel = element.select("a.name span.middle").text();
            String manufacture = manufactureAndModel.trim().substring(0, manufactureAndModel.indexOf(" "));
            parsedCardDto.setManufacture(manufacture);

            if(manufacture.equals("Apple")){
                String model = manufactureAndModel.substring(manufactureAndModel.indexOf(" ")+1, manufactureAndModel.lastIndexOf(" ")).replaceAll(storage.replaceAll("\\D+", ""), "").trim();
                parsedCardDto.setModel(model);
            }else if(manufacture.equals("Xiaomi") || manufacture.equals("Samsung")){
                String model = manufactureAndModel.trim().substring(manufactureAndModel.indexOf(" ")+1);
                parsedCardDto.setModel(model);
            }

            // price
            parsedCardDto.setPrice(
                    Integer.parseInt(
                            element.select("a.price").text().replaceAll("\\D+", "").trim()
                    )
            );
            return parsedCardDto;
    }
}
