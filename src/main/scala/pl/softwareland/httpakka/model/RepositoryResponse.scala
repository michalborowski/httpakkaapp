package pl.softwareland.httpakka.model

case class RepositoryResponse(full_name:String,
                              description:String,
                              clone_url:String,
                              stargazers_count:Double,
                              created_at:String)
