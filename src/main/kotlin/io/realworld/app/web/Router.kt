package io.realworld.app.web

import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.delete
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.put
import io.ktor.routing.route
import io.realworld.app.web.controllers.ArticleController
import io.realworld.app.web.controllers.CommentController
import io.realworld.app.web.controllers.ProfileController
import io.realworld.app.web.controllers.TagController
import io.realworld.app.web.controllers.UserController

fun Routing.users(userController: UserController) {
    route("users") {
        post { call.respond(userController.register(this.context)) }
        post("login") { call.respond(userController.login(this.context)) }
    }
    route("user") {
        get { call.respond(userController.getCurrent(this.context)) }
        put { call.respond(userController.update(this.context)) }
    }
}

fun Routing.profiles(profileController: ProfileController) {
    route("profiles/{username}") {
        get { call.respond(profileController.get(this.context)) }
        route("follow") {
            post { call.respond(profileController.follow(this.context)) }
            delete { call.respond(profileController.unfollow(this.context)) }
        }
    }
}

fun Routing.articles(articleController: ArticleController, commentController: CommentController) {
    route("articles") {
        get("feed") { call.respond(articleController.feed(this.context)) }
        route("{slug}") {
            route("comments") {
                post { call.respond(commentController.add(this.context)) }
                get { call.respond(commentController.findBySlug(this.context)) }
                delete("{id}") { call.respond(commentController.delete(this.context)) }
            }
            route("favorite") {
                post { call.respond(articleController.favorite(this.context)) }
                delete { call.respond(articleController.unfavorite(this.context)) }
            }
            get { call.respond(articleController.get(this.context)) }
            put { call.respond(articleController.update(this.context)) }
            delete { call.respond(articleController.delete(this.context)) }
        }
        get { call.respond(articleController.findBy(this.context)) }
        post { call.respond(articleController.create(this.context)) }
    }
}

fun Routing.tags(tagController: TagController) {
    route("tags") {
        get { tagController.get(this.context) }
    }
}
