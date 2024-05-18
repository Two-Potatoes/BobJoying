package com.twoPotatoes.bobJoying;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * <슬랙에 보내는 payload 형태>
 *
 * {
 *   "attachments":
 *   [
 *     {
 *       "fallback": "PR 리뷰 요청합니다!",
 *       "mrkdwn_in": ["pretext","value"],
 *       "color": "#FFFF00",
 *       "pretext": "@SLACK_ID 님! PR 리뷰 요청합니다! 🙋‍♀️\n\n:arrow_down: 리뷰하러 가기 :arrow_down:",
 *       "fields":
 *       [
 *         {
 *           "title": "Pull Request",
 *           "value": "<PR_URL|PR_TITLE>"
 *         }
 *       ]
 *     }
 *   ]
 * }
 */

public class SlackReviewRequest {
    // 깃허브 ID와 슬랙 ID를 맵핑합니다.
    private Map<String, String> githubIdSlackNick =
        Map.of(
            "JisooPyo", "U06HS6A4HSL",
            "doheuncho", "U06H39U8L2F"
        );

    public static void main(String[] args) {
        SlackReviewRequest slackReviewRequest = new SlackReviewRequest();
        String pretext = slackReviewRequest.generatePretext();
        String prField = slackReviewRequest.generatePRfield();
        String message = slackReviewRequest.generateJsonPayload(pretext, prField);
        slackReviewRequest.sendSlackNotification(message);
    }

    // PR Field
    private String generatePRfield() {
        String prUrl = System.getenv("PR_URL");
        String prTitle = System.getenv("PR_TITLE");
        StringBuilder sb = new StringBuilder();
        sb.append("{\"title\": \"Pull Request\",\"value\": \"<");
        sb.append(prUrl);
        sb.append('|');
        sb.append(prTitle);
        sb.append(">\"}");
        return sb.toString();
    }

    // pretext
    private String generatePretext() {
        String slackId = githubIdSlackNick.get(System.getenv("PR_REVIEWER"));
        StringBuilder sb = new StringBuilder();
        sb.append("\"pretext\": \"");
        sb.append("<@");
        sb.append(slackId);
        sb.append("> 님! PR 리뷰 요청합니다! 🙋‍♀️\n:arrow_down: 리뷰하러 가기 :arrow_down:\"");
        return sb.toString();
    }

    // 보내는 전체 json
    private String generateJsonPayload(String pretext, String prField) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"attachments\":");

        sb.append("[{\"fallback\": \"PR 리뷰 요청합니다! 🙋‍♀️\",");
        sb.append("\"mrkdwn_in\": [\"text\",\"pretext\",\"value\"],");
        sb.append("\"color\": \"#FBF8D9\",");
        sb.append(pretext);
        sb.append(',');

        sb.append("\"fields\":[");
        sb.append(prField);
        sb.append("]}]}");

        return sb.toString();
    }

    // 슬랙에 메시지를 보냅니다.
    private void sendSlackNotification(String message) {
        // yml의 환경변수를 가져옵니다.
        String webhookUrl = System.getenv("SLACK_REVIEW_WEBHOOK_URL");

        try {
            URL url = new URL(webhookUrl);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            String jsonPayload = message;

            // 출력 스트림을 가져와 http 요청 본문에 데이터를 씁니다.
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonPayload.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // 응답 코드에 따라 터미널에 메시지 보내기
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("Message sent successfully to Slack.");
            } else {
                System.err.println("Failed to send message to Slack. Response code: " + responseCode);
            }

            connection.disconnect();
        } catch (Exception e) {
            System.err.println("Exception occurred: " + e.getMessage());
        }
    }
}
