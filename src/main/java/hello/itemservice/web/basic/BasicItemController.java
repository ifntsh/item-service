package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm(){
        return "basic/addForm";
    }

    /**
     * addItemV1 - BasicItemController에 추가
     */
//    @PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                       @RequestParam int price,
                       @RequestParam Integer quantity,
                       Model model){
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);

        model.addAttribute("item", item);

        return "basic/item";
    }

    /**
     * addItemV2 - 상품 등록 처리 - ModelAttribute
     * @ModelAttribute 의 두 가지 기능
     * - 요청 파라미터 처리
     * - Model 추가
     */
//    @PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item
                            //Model model
    ){
        itemRepository.save(item);
      //model.addAttribute("item", item); //@ModelAttribute 사용시 자동 추가 되기 때문에 생략 가능하다.

        return "basic/item";
    }

    /**
     * addItemV3 - 상품 등록 처리 - ModelAttribute 이름 생략
     * @ModelAttribute 의 이름을 생략하면 모델에 저장될 때 클래스명을 사용한다.
     * 이때 클래스의 첫글자만 소문자로 변경해서 등록한다.
     */
//    @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item){
        itemRepository.save(item);
      //model.addAttribute("item", item); //@ModelAttribute 사용시 자동 추가 되기 때문에 생략 가능하다.

        return "basic/item";
    }

    /**
     * addItemV4 - 상품 등록 처리 - ModelAttribute 전체 생략
     * @ModelAttribute 자체도 생략가능하다. 대상 객체는 모델에 자동 등록된다.
     * 나머지 사항은 기존과 동일하다.
     */
    @PostMapping("/add")
    public String addItemV4(Item item){
        itemRepository.save(item);
        return "basic/item";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }


}
