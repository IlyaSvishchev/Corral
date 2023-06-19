package com.example.corral;


import com.example.corral.models.News;
import com.example.corral.repos.NewsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class CorralController {
    @Autowired
    NewsRepo newsRepository;

    @GetMapping("/home")
    public String home() {
        return "corral-main";
    }

    @GetMapping("/news")
    public String corral(Model model) {
        Iterable<News> news = newsRepository.findAll();
        model.addAttribute("news", news);
        return "news-main";
    }

    @GetMapping("/about")
    public String about(Model model) {
        return "about";
    }

    @GetMapping("/news/add")
    public String add(Model model) {
        return "news-add";
    }

    @PostMapping("/news/add")
    public String addData(@RequestParam String name, @RequestParam String fullText) {
        News post = new News(name, fullText);
        newsRepository.save(post);
        return "news-main";
    }

    @GetMapping("/news/{id}")
    public String newsDetails(@PathVariable(value = "id") long id, Model model) {
        Optional<News> newsDetails = newsRepository.findById(id);
        ArrayList<News> result = new ArrayList<>();
        newsDetails.ifPresent(result::add);
        model.addAttribute("newsDetails", result);
        if (!newsRepository.existsById(id)) {
            return "news-main";
        }
        return "news-details";
    }

    @GetMapping("/news/{id}/edit")
    public String newsEdit(@PathVariable(value = "id") long id, Model model) {
        Optional<News> newsDetails = newsRepository.findById(id);
        ArrayList<News> result = new ArrayList<>();
        newsDetails.ifPresent(result::add);
        model.addAttribute("newsDetails", result);
        if (!newsRepository.existsById(id)) {
            return "news-main";
        }
        return "news-edit";

    }
    @PostMapping("/news/{id}/edit")
    public String updateData(@PathVariable(value = "id") long id, @RequestParam String name, @RequestParam String fullText) {
        News newsDetails = newsRepository.findById(id).orElseThrow();
        newsDetails.setName(name);
        newsDetails.setFullText(fullText);
        newsRepository.save(newsDetails);
        return "news-edit";

    }
    @GetMapping("/news/{id}/remove")
    public String removeData(@PathVariable(value = "id") long id, Model model){
        newsRepository.deleteById(id);
        return "news-remove";
    }


}
