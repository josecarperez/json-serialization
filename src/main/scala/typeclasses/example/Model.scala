package typeclasses.example

import java.util.Date

case class User(name: String, age: Int, email: String)
case class Post(content: String, createdAt: Date)
case class Feed(user: User, posts: List[Post])
