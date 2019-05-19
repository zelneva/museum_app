package dev.android.museum.api

import dev.android.museum.model.*
import dev.android.museum.model.util.LoginObject
import dev.android.museum.model.util.SessionObject
import io.reactivex.Observable
import okhttp3.MultipartBody
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
    fun createExhibition(@Field("title") name: String,
                         @Field("startsAt") startsAt: String,
                         @Field("endsAt") endsAt: String,
                         @Field("museumId") museumId: String,
                         @Field("session") session: String): Observable<Unit>


    @DELETE("exhibition/{id}")
    fun deleteExhibitionById(@Path("id") id: String,
                             @Query("session") session: String): Observable<Unit>


    @FormUrlEncoded
    @PUT("exhibition/{id}")
    fun updateExhibition(@Path("id") id: String,
                         @Field("title") name: String,
                         @Field("startsAt") startsAt: String,
                         @Field("endsAt") endsAt: String,
                         @Field("museumId") museumId: String,
                         @Field("session") session: String): Observable<Unit>


    /*
    * Showpiece
    */

    @GET("showpiece")
    fun getAllShowpiece(): Observable<ArrayList<Showpiece>>


    @GET("showpiece/{id}")
    fun getShowpieceById(@Path("id") id: String): Observable<Showpiece>

    @Multipart
    @POST("showpiece")
    @FormUrlEncoded
    fun createShowpiece(@Part("srcPhoto") image: MultipartBody.Part,
                        @Field("year") year: String,
                        @Field("authorName") authorId: String,
                        @Field("titleRus") titleRus: String,
                        @Field("descriptionRus") descRus: String,
                        @Field("titleEng") titleEng: String,
                        @Field("descriptionEng") descEng: String,
                        @Field("titleGer") titleGer: String,
                        @Field("descriptionGer") descriptionGer: String,
                        @Field("session") sessionId: String
    ): Observable<Unit>


    @FormUrlEncoded
    @PUT("showpiece/{id}")
    fun updateShowpiece(@Path("id") id: String,
                        @Part("srcPhoto") image: MultipartBody.Part?,
                        @Field("year") year: String,
                        @Field("authorName") authorId: String,
                        @Field("titleRus") titleRus: String,
                        @Field("descriptionRus") descRus: String,
                        @Field("titleEng") titleEng: String,
                        @Field("descriptionEng") descEng: String,
                        @Field("titleGer") titleGer: String,
                        @Field("descriptionGer") descriptionGer: String,
                        @Field("session") sessionId: String): Observable<Unit>


    @GET("showpiece/exhibition/{exhibitionId}")
    fun getListShowpieceByExhibitionId(@Path("exhibitionId") exhibitionId: String): Observable<List<Showpiece>>


    @GET("showpiece/author/{authorId}")
    fun getListShowpieceByAuthorId(@Path("authorId") authorId: String): Observable<List<Showpiece>>


    @FormUrlEncoded
    @PUT("showpiece/exhibition/{showpieceId}")
    fun updateExhibitionForShowpiece(@Path("showpieceId") id: String, exhibitionId: String, sessionId: String): Observable<Unit>


    @GET("locale/showpiece")
    fun getLocaleDataShowpieceById(@Query("id") id: String): Observable<ArrayList<ShowpieceLocaleData>>


    @DELETE("locale/showpiece/{id}")
    fun deleteLocaleDataShowpiece(@Path("id") id: String): Observable<Unit>


    @FormUrlEncoded
    @PUT("locale/showpiece/{id}")
    fun updateLocaleDataShowpiece(@Path("id") id: String,
                                  @Field("showpiece.id") showpieceId: String,
                                  @Field("language") language: String,
                                  @Field("name") name: String,
                                  @Field("description") description: String): Observable<Unit>


    @FormUrlEncoded
    @PUT("locale/showpiece")
    fun addShowpieceToExhibition(@Field("list") list: Array<String?>,
                                 @Field("exhibitionId") exhibitionId: String,
                                 @Field("session") session: String): Observable<Boolean>





    /*
    *  Author
    */

    @GET("author")
    fun getAllAuthors(): Observable<ArrayList<Author>>


    @GET("author/{id}")
    fun getAuthorById(@Path("id") id: String): Observable<Author>

    @FormUrlEncoded
    @POST("author")
    fun createAuthor(@Part("srcPhoto") image: MultipartBody.Part,
                     @Field("bornAt") year: String,
                     @Field("deadAt") authorId: String,
                     @Field("titleRus") titleRus: String,
                     @Field("descriptionRus") descRus: String,
                     @Field("titleEng") titleEng: String,
                     @Field("descriptionEng") descEng: String,
                     @Field("titleGer") titleGer: String,
                     @Field("descriptionGer") descriptionGer: String,
                     @Field("session") sessionId: String
    ): Observable<Unit>


    @DELETE("author/{id}")
    fun deleteAuthor(@Path("id") id: String, session: String): Observable<Unit>

    @FormUrlEncoded
    @PUT("author/{id}")
    fun updateAuthor(@Path("id") id: String,
                     @Part("srcPhoto") image: MultipartBody.Part,
                     @Field("bornAt") year: String,
                     @Field("deadAt") authorId: String,
                     @Field("titleRus") titleRus: String,
                     @Field("descriptionRus") descRus: String,
                     @Field("titleEng") titleEng: String,
                     @Field("descriptionEng") descEng: String,
                     @Field("titleGer") titleGer: String,
                     @Field("descriptionGer") descriptionGer: String,
                     @Field("session") sessionId: String): Observable<Unit>


    @GET("locale/author")
    fun getLocaleDataAuthor(@Query("id") id: String): Observable<ArrayList<AuthorLocaleData>>


    @DELETE("locale/author/{id}")
    fun deleteLocaleDataAuthor(@Path("id") id: String, session: String): Observable<Unit>


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

    @FormUrlEncoded
    @PUT("comment/{id}")
    fun updateComment(@Path("id") id: String,
                      @Field("text") text: String,
                      @Field("session") sessionId: String): Observable<Unit>


    @GET("comment")
    fun getListCommentByShowpiece(@Query("showpieceId") showpieceId: String): Observable<ArrayList<Comment>>
}
