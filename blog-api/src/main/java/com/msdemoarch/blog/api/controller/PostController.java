package com.msdemoarch.blog.api.controller;

import com.msdemoarch.blog.api.dto.PostDTO;
import com.msdemoarch.blog.domain.port.service.PostService;
import com.msdemoarch.blog.api.mapper.DTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.FormFieldPart;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;


@RestController
@RequestMapping("/api/posts")
public class PostController {
    
    private PostService postService;
    private DTOMapper mapper;
    
    @Autowired
    public PostController(PostService postService, DTOMapper mapper) {
        this.postService = postService;
        this.mapper = mapper;
    }

    @PostMapping(value = "/uploadWithMetadata", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public Mono<Void> uploadWithMetadata(@RequestPart(value = "file", required = false) Mono<FilePart> file, 
                                         @RequestPart(value = "contentType", required = false) FormFieldPart description) {

        Mono<Path> tmpPath = file.map(fp -> {
            String fileName = UUID.randomUUID().toString().concat("_").concat(fp.filename());
            Path tmpLocation = Paths.get(System.getProperty("java.io.tmpdir"), fileName);
            fp.transferTo(tmpLocation.toFile());
            return tmpLocation;
        });

        System.out.println("desc: " + description.value());
        tmpPath.subscribe(System.out::println);
        return Mono.empty();
    }

    @GetMapping
    public Flux<PostDTO> all() {
        return this.postService.findAllPosts()
                .map(this.mapper::mapBoToDto);
    }

    @PostMapping
    public Mono<ResponseEntity<PostDTO>> create(@RequestBody PostDTO post) {
        return this.postService
                .addPost(this.mapper.mapDtoToBo(post))
                .map((bo) -> new ResponseEntity<>(this.mapper.mapBoToDto(bo), HttpStatus.CREATED));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<PostDTO>> get(@PathVariable("id") String id) {
        return this.postService.findPostById(id)
                .map(bo -> new ResponseEntity<>(this.mapper.mapBoToDto(bo), HttpStatus.OK));
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable("id") String id) {
        return this.postService.deletePostById(id);
    }
}
