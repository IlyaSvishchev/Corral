package com.example.corral;


import com.example.corral.models.News;
import com.example.corral.repos.NewsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CorralController {
    @Autowired
    NewsRepo newsRepository;
    private String title;
    private String full_text;
    private Model model;

    @GetMapping("/home")
    public String home(){
        return "corral-main";
    }
    @GetMapping("/news")
    public String corral(Model model){
        Iterable<News> news = newsRepository.findAll();
        model.addAttribute("news", news);
        return "news-main";
    }
    @GetMapping("/about")
    public String about(Model model){
        return "about";
    }

    @GetMapping("/news/add")
    public String add(Model model){
        return "news-add";
    }
    @PostMapping("/news/add")
    public String addData(@RequestParam String name, @RequestParam String fullText, Model model){
        News post = new News(name, fullText);
        newsRepository.save(post);
        return "news-main";
    }
}
