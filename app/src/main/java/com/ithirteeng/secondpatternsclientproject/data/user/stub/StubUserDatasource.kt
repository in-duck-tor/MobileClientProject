package com.ithirteeng.secondpatternsclientproject.data.user.stub

import com.ithirteeng.secondpatternsclientproject.domain.user.model.AuthTokenResponse
import com.ithirteeng.secondpatternsclientproject.domain.user.model.Role
import com.ithirteeng.secondpatternsclientproject.domain.user.model.UserAuthData
import com.ithirteeng.secondpatternsclientproject.domain.user.repository.UserRemoteDatasource

class StubUserDatasource : UserRemoteDatasource {

    override suspend fun authorize(data: UserAuthData): AuthTokenResponse {
        if (data.login == ivan.login && data.password == password) return ivan
        if (data.login == alena.login && data.password == password) return alena
        if (data.login == newUser.login && data.password == password) return newUser
        throw Exception("User not found")
    }

    private companion object {

        private val ivan = AuthTokenResponse(
            login = "gulevskiy.ivan",
            roles = listOf(Role.client),
            token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MTI0ODQyMjcsImV4cCI6MTcyMjQ4NDIyNywiaXNzIjoiaW4tZHVjay10b3IiLCJjbGllbnRfaWQiOiJhbmd1bGFyX3NwYSIsInN1YiI6IjEiLCJhdXRoX3RpbWUiOjE3MTI0ODQyMjcsImlkcCI6ImxvY2FsIiwiYWNjb3VudF90eXBlIjoiY2xpZW50IiwibG9naW4iOiJndWxldnNraXkuaXZhbiIsInJvbGVzIjoiY2xpZW50IiwianRpIjoiRjJGNjg0OUVBNzYxREVEOERCMzVFOEYzQkZCMjIyRDgiLCJzaWQiOiJBQkQ0QTRFRUFFMUMxMTA2QjE5MUU1NzdBMkUwNzBCMCIsImlhdCI6MTcxMjQ4NDIyNywic2NvcGUiOlsib3BlbmlkIiwicHJvZmlsZSIsImVtYWlsIl0sImFtciI6WyJwd2QiXX0.BVgN7S8tYwb4DNY06UhR-oC0eZbT3pDsVDMWST3Y9nGFQ5JOwQtcvWay0hfmWTjl32KKkniOPsfQhTRwXbzp1yW1ksPOcS0mnP9FRVrXQHL3rqBTopXrTNoOtmJzO4RQsQJG5pi9HgSiJxP3YBTfjwDsJzlizHRhSCIFeOQPDRP0fh51bf6jc93Qs0MdmGwj2QHtwRkcJkUKgLecKCX4gc3WfKVow4iTYocuxeT6UcvgW_Y62tfbmsUwKL2nP8h69zVucZX4IuJAjByD27Vgqgx8WCtTjbYwtvfmC4V2LDsohBjGc1T8eUfYfjp04ZW8YIV_1FnEz057-vHKTjaesw"
        )

        private const val password = "123456"

        private val alena = AuthTokenResponse(
            login = "al.v.tarasova",
            roles = listOf(Role.client, Role.employee),
            token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MTI0ODQxNTYsImV4cCI6MTcyMjQ4NDE1NiwiaXNzIjoiaW4tZHVjay10b3IiLCJjbGllbnRfaWQiOiJhbmd1bGFyX3NwYSIsInN1YiI6IjIiLCJhdXRoX3RpbWUiOjE3MTI0ODQxNTUsImlkcCI6ImxvY2FsIiwiYWNjb3VudF90eXBlIjoiY2xpZW50IiwibG9naW4iOiJhbC52LnRhcmFzb3ZhIiwicm9sZXMiOlsiZW1wbG95ZWUiLCJjbGllbnQiXSwicGVybWlzc2lvbnMiOlsiYWNjb3VudC5tYW5hZ2UiLCJhY2NvdW50LnJlYWQiLCJ1c2VyLm1hbmFnZSIsInVzZXIucmVhZCJdLCJqdGkiOiJDRTNCMjdBREMxRjM5QkU5OUQxRjZGMEYxOTdCMUVDMiIsInNpZCI6IjkyRUFGMTk2MzdDMDg5MDkzRDI1RjYxNzBGOTY0NzIzIiwiaWF0IjoxNzEyNDg0MTU2LCJzY29wZSI6WyJvcGVuaWQiLCJwcm9maWxlIiwiZW1haWwiXSwiYW1yIjpbInB3ZCJdfQ.KDpmvrFog9raRX5vPrcka7Chvw7JTrnvlpZI0oOMhiCR8zYt3VCv1QWQ9IiVT7JMpQ6WuxhHUH7yIg6vzKAliJF8cHp6vk_FMg77AGZOQyE4qgulq9ONGCPnONEX5yWgGLhCvBUV6JRf_m0Tz5Qn9UQCQoqkG8T_sMNE8z-3OxN14n-iSiDr6BeHy-yRasL-UDOfaj9m3JFD51TgafiyQnM-qgTCHXkqo7noYROEHAb2o9YetmPzsDydPL99dv94-Gh8pkWLyyuOTBA9osi5ERn0sLYRSGX9yBXMqZgI-1MWa-dGg2b4mLB_UR4dbehNOA0FUwtxKm0EM6tEYxfnOA"
        )

        private val newUser = AuthTokenResponse(
            login = "new_client",
            roles = listOf(Role.client),
            token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MTI0ODQ4NjUsImV4cCI6MTcyMjQ4NDg2NSwiaXNzIjoiaW4tZHVjay10b3IiLCJjbGllbnRfaWQiOiJhbmd1bGFyX3NwYSIsInN1YiI6IjE1IiwiYXV0aF90aW1lIjoxNzEyNDg0ODY1LCJpZHAiOiJsb2NhbCIsImFjY291bnRfdHlwZSI6ImNsaWVudCIsImxvZ2luIjoibmV3X2NsaWVudCIsInJvbGVzIjoiY2xpZW50IiwianRpIjoiQzVCNkI5QTNGN0UyMjU4RDY1ODRFMjdFRjJDRjkyMkQiLCJzaWQiOiI2MDU5RENENTg5MEUzMTExMkQ0M0I3QTg5RTE2NUEyMCIsImlhdCI6MTcxMjQ4NDg2NSwic2NvcGUiOlsib3BlbmlkIiwicHJvZmlsZSIsImVtYWlsIl0sImFtciI6WyJwd2QiXX0.1NPykWbld5M8hT6TrufU_-vYcioFr47xqgSWW8D9jBQOhw6ceKuWsQ985OvrqDsDJ4OwkPMR5bh23Emn-8n9HbGjQd3npcwfH6nCff9UnKUhqx7bCPx5jcxBKT8xEXzuk6KxzWiHy_O2v5BdT3KbEEJEBPtCL5H-juXOJ5Xy0O7lL4yKAz01H6GLXFfK2nhw80QFe4H2ZgMKFA7bGfDhEJE0wjNXicAAHAkc8mVkpPHxKZL2w4tWmgwTdkvfA8iZiVLV9XM-MRv0tmGk1JoKa17HGJeB3Qek2QeJMfmJY8Q40ffp1a8Z7B4_vy4DHK1OQLtTHDO81qxeiAf5XGMYKg"
        )
    }
}