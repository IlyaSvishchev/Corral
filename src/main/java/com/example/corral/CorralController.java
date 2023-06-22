package com.example.corral;


import com.example.corral.models.News;
import com.example.corral.repos.NewsRepo;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class CorralController {
    final
    NewsRepo newsRepository;

    public CorralController(NewsRepo newsRepository) {
        this.newsRepository = newsRepository;
    }
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
    public String addData(@RequestParam String name, @RequestParam String fullText, @RequestParam String author) {
        News post = new News(name, fullText, author);
        newsRepository.save(post);
        return "news-main";
    }
    @GetMapping("/news/{name}")
    public String newsOptional(@PathVariable(value="name") String name, Model model){
        Optional<News> newsDetails = newsRepository.findByName(name);
        ArrayList<News> res = new ArrayList<>();
        newsDetails.ifPresent(res::add);
        model.addAttribute("newsDetails", res);
        if(!newsRepository.existsByName(name)){
            return "news-main";
        }
        return "news-details";
    }
    @GetMapping("/news/{name}/edit")
    public String newsEdit(@PathVariable(value="name") String name, Model model){
        Optional<News> newsDetails = newsRepository.findByName(name);
        ArrayList<News> res = new ArrayList<>();
        newsDetails.ifPresent(res::add);
        model.addAttribute("newsDetails", res);
        if(!newsRepository.existsByName(name)){
            return "news-fail";
        }
        return "news-edit";
    }
    @PostMapping("/news/{name}/edit")
    public String updateData(@PathVariable(value = "name") String name, @RequestParam String author, @RequestParam String fullText ){
        News newsDetails = newsRepository.findByName(name).orElseThrow();
        newsDetails.setAuthor(author);
        newsDetails.setFullText(fullText);
        newsRepository.save(newsDetails);
        return "redirect:/news/{name}";
    }
    @GetMapping("/news/{name}/remove")
    @Transactional
    public String removeData(@PathVariable(value = "name") String name){
        newsRepository.deleteByName(name);
        return "news-remove";
    }
}