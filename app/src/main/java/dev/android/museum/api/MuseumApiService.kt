package dev.android.museum.api

import dev.android.museum.model.*
import dev.android.museum.model.util.LoginObject
import dev.android.museum.model.util.SessionObject
import io.reactivex.Observable
import retrofit2.http.*
import java.util.*

interface MuseumApiService {

    /*
     * Request by museum
     */

    @GET("museum")
    fun getAllMuseum(): Observable<ArrayList<Museum>>


    @GET("museum")
    fun getMuseumByName(@Query("name") name: String): Observable<Museum>


    @GET("museum/{id}")
    fun getMuseumById(@Path("id") id: String): Observable<Museum>


    @FormUrlEncoded
    @POST("museum")
    fun createMuseum(@Field("name") name: String,
                     @Field("address") address: String,
                     @Field("session") session: String): Observable<Unit>


    @DELETE("museum/{id}")
    fun deleteMuseumById(@Path("id") id: String,
                         @Query("session") session: String): Observable<Unit>

    @FormUrlEncoded
    @PUT("museum/{id}")
    fun updateMuseum(@Path("id") id: String,
                     @Field("name") name: String,
                     @Field("address") address: String,
                     @Field("session") session: String): Observable<Unit>


    /*
     * Exhibition
     */

    @GET("exhibition")
    fun getAllExhibition(): Observable<List<Exhibition>>


    @GET("exhibition/{id}")
    fun getExhibitionById(@Path("id") id: String): Observable<Exhibition>


    @GET("exhibition/museum/{museumId}")
    fun getExhibitionsByMuseumId(@Path("museumId") museumId: String): Observable<ArrayList<Exhibition>>


    @POST("exhibition")
    @FormUrlEncoded
    fun createExhibition(@Field("name") name: String,
                         @Field("startsAt") startsAt: Date,
                         @Field("endsAt") endsAt: Date,
                         @Field("museum.id") museumId: String): Observable<Unit>


    @DELETE("exhibition/{id}")
    fun deleteExhibitionById(@Path("id") id: String): Observable<Unit>


    @PUT("exhibition/{id}")
    fun updateExhibition(@Path("id") id: String,
                         @Query("name") name: String?,
                         @Query("startsAt") startsAt: Date?,
                         @Query("endsAt") endsAt: Date?,
                         @Query("museum.id") museumId: String?): Observable<Unit>


    /*
    * Showpiece
    */

    @GET("showpiece")
    fun getAllShowpiece(): Observable<ArrayList<Showpiece>>


    @GET("showpiece/{id}")
    fun getShowpieceById(@Path("id") id: String): Observable<Showpiece>


    @POST("showpiece")
    @FormUrlEncoded
    fun createShowpiece(@Field("exhibition.id") exhibitionId: String,
                        @Field("author.id") authorId: String,
                        @Field("date") date: String,
                        @Field("genre") genre: String,
                        @Field("srcPhoto") srcPhoto: String): Observable<Unit>


    @PUT("showpiece/{id}")
    fun updateShowpiece(@Path("id") id: String,
                        @Field("exhibition.id") exhibitionId: String,
                        @Field("author.id") authorId: String,
                        @Field("date") date: String,
                        @Field("genre") genre: String,
                        @Field("srcPhoto") srcPhoto: String): Observable<Unit>


    @GET("showpiece/exhibition/{exhibitionId}")
    fun getListShowpieceByExhibitionId(@Path("exhibitionId") exhibitionId: String): Observable<List<Showpiece>>


    @GET("showpiece/author/{authorId}")
    fun getListShowpieceByAuthorId(@Path("authorId") authorId: String): Observable<List<Showpiece>>


    @GET("locale/showpiece")
    fun getLocaleDataShowpieceById(@Query("id") id: String): Observable<ArrayList<ShowpieceLocaleData>>


    @POST("locale/showpiece")
    fun createLocaleDataShowpiece(@Field("showpiece.id") showpieceId: String,
                                  @Field("language") language: String,
                                  @Field("name") name: String,
                                  @Field("description") description: String): Observable<Unit>


    @DELETE("locale/showpiece/{id}")
    fun deleteLocaleDataShowpiece(@Path("id") id: String): Observable<Unit>


    @PUT("locale/showpiece/{id}")
    fun updateLocaleDataShowpiece(@Path("id") id: String,
                                  @Field("showpiece.id") showpieceId: String,
                                  @Field("language") language: String,
                                  @Field("name") name: String,
                                  @Field("description") description: String): Observable<Unit>

    /*
    *  Author
    */

    @GET("author")
    fun getAllAuthors(): Observable<ArrayList<Author>>


    @GET("author/{id}")
    fun getAuthorById(@Path("id") id: String): Observable<Author>


    @POST("author")
    fun createAuthor(@Field("bornAt") bornAt: Date,
                     @Field("diedAt") diedAt: Date?): Observable<Unit>


    @DELETE("author/{id}")
    fun deleteAuthor(@Path("id") id: String): Observable<Unit>


    @PUT("author/{id}")
    fun updateAuthor(@Field("bornAt") bornAt: Date,
                     @Field("diedAt") diedAt: Date?): Observable<Unit>


    @GET("locale/author")
    fun getLocaleDataAuthor(@Query("id") id: String): Observable<ArrayList<AuthorLocaleData>>


    @POST("locale/author")
    fun createLocaleDataAuthor(@Field("name") name: String,
                               @Field("language") language: String,
                               @Field("description") description: String,
                               @Field("author.id") authorId: String): Observable<Unit>


    @DELETE("locale/author/{id}")
    fun deleteLocaleDataAuthor(@Path("id") id: String): Observable<Unit>


    @PUT("locale/author/{id}")
    fun updateLocaleDataAuthor(@Path("id") id: String,
                               @Field("name") name: String,
                               @Field("language") language: String,
                               @Field("description") description: String,
                               @Field("author.id") authorId: String): Observable<Unit>


    /*
    *  Users
    */

    @FormUrlEncoded
    @POST("user/register")
    fun registration(@Field("username") login: String,
                     @Field("password") password: String,
                     @Field("name") name: String): Observable<SessionObject>


    @POST("user/login")
    fun login(@Body loginObject: LoginObject): Observable<SessionObject>

    @FormUrlEncoded
    @POST("user/logout")
    fun logout(@Field("sessionId") sessionId: String): Observable<Any>


    @DELETE("user/{id}")
    fun deleteUser(@Query("sessionId") sessionId: String): Observable<Unit>

    @FormUrlEncoded
    @PUT("user/{id}")
    fun updateUser(@Path("id") id: String,
                   @Field("name") name: String,
                   @Field("username") username: String,
                   @Field("password") password: String,
                   @Field("sessionId") sessionId: String): Observable<User>


    @GET("user/{id}")
    fun getUserInfo(@Path("id") id: String): Observable<User>


    /*
     * Favorite
     */

    @FormUrlEncoded
    @POST("favorite")
    fun createFavorite(@Field("showpiece") showpieceId: String,
                       @Field("session") sessionId: String): Observable<Unit>


    @DELETE("favorite/{id}")
    fun deleteFavorite(@Path("id") id: String,
                       @Query("session") sessionId: String): Observable<Unit>


    @GET("favorite")
    fun getFavorite(@Field("session") sessionId: String): Observable<List<Favorite>>

    /*
     * Comment
     */

    @FormUrlEncoded
    @POST("comment")
    fun createComment(@Field("showpiece") showpieceId: String,
                      @Field("text") text: String,
                      @Field("session") sessionId: String): Observable<Unit>


    @DELETE("comment/{id}")
    fun deleteComment(@Path("id") id: String,
                      @Query("session") sessionId: String): Observable<Unit>


    @PUT("comment/{id}")
    fun updateComment(@Path("id") id: String,
                      @Field("text") text: String,
                      @Field("session") sessionId: String): Observable<Unit>


    @GET("comment")
    fun getListCommentByShowpiece(@Query("showpieceId") showpieceId: String): Observable<ArrayList<Comment>>
}
