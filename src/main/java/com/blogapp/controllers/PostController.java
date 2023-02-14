package com.blogapp.controllers;

import com.blogapp.config.AppConstant;
import com.blogapp.impl.FileServiceImpl;
import com.blogapp.impl.PostServiceImpl;
import com.blogapp.payload.APIResponse;
import com.blogapp.payload.PostDto;
import com.blogapp.payload.PostResponse;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostServiceImpl postServiceImpl;

    @Autowired
    private FileServiceImpl fileServiceImpl;

    @Value("${project.image}")
    private String path;

    @PostMapping("/user/{userId}/category/{categId}/posts")
    public ResponseEntity<PostDto> createPost(
            @RequestBody PostDto postDto,
            @PathVariable("userId") Integer userId,
            @PathVariable("categId") Integer categId
    ){
        PostDto post = this.postServiceImpl.createPost(postDto, userId, categId);
        return  new ResponseEntity<>(post, HttpStatus.CREATED);

    }

//    post getby User Handler
    @GetMapping("/user/{userId}")
    public ResponseEntity<PostResponse> getPostOfUser(
            @PathVariable("userId") Integer userId,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize
    ){
        PostResponse allUserPosts = this.postServiceImpl.getPostByUser(userId, pageNumber, pageSize);
        return new ResponseEntity<>(allUserPosts, HttpStatus.OK);
    }

    @GetMapping("/categ/{catgId}")
    public ResponseEntity<PostResponse> getAllPostOfCateg(
            @PathVariable("catgId") Integer catgId,
            @RequestParam(value = "pageNumber",defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value="pageSize", defaultValue = "5", required = false) Integer pageSize
            ){

        PostResponse postByCategory = this.postServiceImpl.getPostByCategory(catgId, pageNumber, pageSize);
        return new ResponseEntity<>(postByCategory,HttpStatus.OK);
    }

//    get single Post
    @GetMapping("/post/{postId}")
    public  ResponseEntity<PostDto> getPostById(@PathVariable("postId") Integer postId){
        PostDto postDto = this.postServiceImpl.getPost(postId);
        return new ResponseEntity<PostDto>(postDto, HttpStatus.OK);
    }

//    get all post
    @GetMapping("/post")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstant.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir
    ){
        PostResponse allPost = this.postServiceImpl.getAllPost(pageNumber, pageSize,sortBy, sortDir);
        return  new ResponseEntity<>(allPost, HttpStatus.OK);
    }

    //    delete single Post
    @DeleteMapping("/post/{postId}")
    public  ResponseEntity<APIResponse> deletePostById(@PathVariable("postId") Integer postId){
        this.postServiceImpl.deletePost(postId);
        return ResponseEntity.ok(new APIResponse("delete the post",HttpStatus.OK,true));
    }

//    update Post
    @PutMapping("/post/{postId}")
    public  ResponseEntity<PostDto> updatePost(@PathVariable("postId")Integer postId, @RequestBody PostDto postDto){
        PostDto postDto1 = this.postServiceImpl.UpdatePost(postDto, postId);
        return  new ResponseEntity<PostDto>(postDto1, HttpStatus.OK);
    }

//    seach post-all post
    @GetMapping("/post/search_title/{title-search}")
    public ResponseEntity<List<PostDto>> searchPostByTitle(
            @PathVariable("title-search") String titleSearch
    ){
        List<PostDto> postDtos = this.postServiceImpl.searchPost(titleSearch);
        return  new ResponseEntity<>(postDtos, HttpStatus.OK);
    }

//    search the text in the postContent
    @GetMapping("/post/search_content/{keyword}")
    public ResponseEntity<List<PostDto>> getAllSearchContent(
            @PathVariable("keyword") String keyWords
    ){
        List<PostDto> postDtos = this.postServiceImpl.searchContent(keyWords);
        return new ResponseEntity<>(postDtos,HttpStatus.OK);
    }

//    post image Upload
    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(
            @RequestParam("image")MultipartFile file,
            @PathVariable("postId") Integer postId
            ) throws IOException {

//        if post not found it will throw exception
        System.out.println("path "+ path);
        PostDto postDto = this.postServiceImpl.getPost(postId);
        String fileName = this.fileServiceImpl.uploadImage(path, file);
        postDto.setPostImage(fileName);
        PostDto postDto1 = this.postServiceImpl.UpdatePost(postDto, postId);
        return new ResponseEntity<>(postDto1, HttpStatus.OK);
    }


//    not recommended method
//    @GetMapping("/post/user/serch_title/{title-search}")
//   public ResponseEntity<List<PostDto>> searchTitleInUserPost(
//           @PathVariable("title-search") String titleSearch
//    ){
//        List<PostDto> allPost = this.searchPostByTitle(titleSearch).getBody();
//        List<PostDto> allUserPost = allPost.stream()
//                .filter(postDto -> postDto.getUser().getName().equalsIgnoreCase("pooja"))
//                .collect(Collectors.toList());
//        return new ResponseEntity<>(allUserPost, HttpStatus.OK);
//    }


}
