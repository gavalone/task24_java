package com.example.task24;

import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.Date;
import java.util.Collections;
import java.text.SimpleDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param; //привязка пераметров
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.Comparator;

@Controller
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @RequestMapping("/") // Главная страница
    public String index(Model model, @Param("keyword") String keyword) {
        List<Goods> goodsList = goodsService.ListAll(keyword);

        Map<Date, Integer> dateMap = new HashMap<>();

        for (Goods goods : goodsList) {
            Date dateGood = goods.getDateOfArrival();
            dateMap.put(dateGood, dateMap.getOrDefault(dateGood, 0) + 1);
        }

        List<List<Object>> dateCountMap = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (Map.Entry<Date, Integer> entry : dateMap.entrySet()) {
            List<Object> subList = new ArrayList<>();
            subList.add(sdf.format(entry.getKey()));
            subList.add(entry.getValue());
            dateCountMap.add(subList);
        }

        Collections.sort(dateCountMap, new Comparator<List<Object>>() {
            public int compare(List<Object> o1, List<Object> o2) {
                try {
                    Date date1 = sdf.parse((String) o1.get(0));
                    Date date2 = sdf.parse((String) o2.get(0));
                    return date1.compareTo(date2);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        model.addAttribute("chartData", dateCountMap);
        model.addAttribute("goodsList", goodsList);
        model.addAttribute("keyword", keyword);
        return "index"; // index.html
    }

    @RequestMapping("/new")
    public String newGoods(Model model){
        Goods goods = new Goods();
        model.addAttribute("goods", goods);
        return "new";
    }

    @RequestMapping(value="/save", method = RequestMethod.POST)
    public String saveGoods(@ModelAttribute("goods") Goods goods){
        goodsService.save(goods);
        return "redirect:/";
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView editGoods(@PathVariable(name="id") Long id){
        ModelAndView mav = new ModelAndView("edit_good");
        Goods goods = goodsService.get(id);
        mav.addObject("goods", goods);
        return mav;
    }

    @RequestMapping("/delete/{id}")
    public String deleteGoods(@PathVariable Long id){
        goodsService.delete(id);
        return "redirect:/";
    }

    @RequestMapping("sort")
    public  String sortHomePage(Model model, @Param("keyword") String keyword){
        List<Goods> goodsList = goodsService.ListAll(keyword);
        goodsList.sort(Comparator.comparing(Goods::getDateOfArrival));

        Map<Date, Integer> dateMap = new HashMap<>();

        for (Goods good : goodsList) {
            Date dateGoods = good.getDateOfArrival();
            dateMap.put(dateGoods, dateMap.getOrDefault(dateGoods, 0) + 1);
        }

        List<List<Object>> dateCountMap = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (Map.Entry<Date, Integer> entry : dateMap.entrySet()) {
            List<Object> subList = new ArrayList<>();
            subList.add(sdf.format(entry.getKey()));
            subList.add(entry.getValue());
            dateCountMap.add(subList);
        }

        Collections.sort(dateCountMap, new Comparator<List<Object>>() {
            public int compare(List<Object> o1, List<Object> o2) {
                try {
                    Date date1 = sdf.parse((String) o1.get(0));
                    Date date2 = sdf.parse((String) o2.get(0));
                    return date1.compareTo(date2);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        model.addAttribute("chartData", dateCountMap);
        model.addAttribute("goodsList", goodsList);
        model.addAttribute("keyword", keyword);
        return "index";
    }
}
