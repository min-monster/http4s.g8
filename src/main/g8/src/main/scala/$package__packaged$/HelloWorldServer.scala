package $package$

import cats.effect.{ConcurrentEffect, Effect, ExitCode, IO, IOApp}
import cats.implicits._
import org.http4s.server.blaze.BlazeBuilder

object HelloWorldServer extends IOApp {
  def run(args: List[String]) =
    ServerStream.stream[IO].compile.drain.as(ExitCode.Success)
}

object ServerStream {
  def helloWorldRoutes[F[_]: Effect] = new HelloWorldRoutes[F].routes

  def stream[F[_]: ConcurrentEffect] =
    BlazeBuilder[F]
      .bindHttp(8080, "0.0.0.0")
      .mountService(helloWorldRoutes, "/")
      .serve
}
