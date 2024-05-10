package com.example.model.hotMatches


import com.google.gson.annotations.SerializedName

data class HotMatche(
    @SerializedName("agg_score")
    val aggScore: List<Int?>?,
    @SerializedName("away_Info")
    val awayInfo: AwayInfo?,
    @SerializedName("away_position")
    val awayPosition: String?,
    @SerializedName("away_team_id")
    val awayTeamId: String?,
    @SerializedName("competition_id")
    val competitionId: String?,
    @SerializedName("coverage")
    val coverage: Coverage?,
    @SerializedName("environment")
    val environment: Environment?,
    @SerializedName("home_Info")
    val homeInfo: HomeInfo?,
    @SerializedName("home_position")
    val homePosition: String?,
    @SerializedName("home_team_id")
    val homeTeamId: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("league_Info")
    val leagueInfo: LeagueInfo?,
    @SerializedName("match_time")
    val matchTime: Int?,
    @SerializedName("match_timing")
    val matchTiming: String?,
    @SerializedName("neutral")
    val neutral: Int?,
    @SerializedName("note")
    val note: String?,
    @SerializedName("odds")
    val odds: Odds?,
    @SerializedName("referee_id")
    val refereeId: String?,
    @SerializedName("related_id")
    val relatedId: String?,
    @SerializedName("round")
    val round: Round?,
    @SerializedName("season_id")
    val seasonId: String?,
    @SerializedName("status_id")
    val statusId: Int?,
    @SerializedName("update_timing")
    val updateTiming: String?,
    @SerializedName("updated_at")
    val updatedAt: Int?,
    @SerializedName("venueDetails")
    val venueDetails: VenueDetails?,
    @SerializedName("venue_id")
    val venueId: String?
)