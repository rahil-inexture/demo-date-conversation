<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>API Documentation</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            line-height: 1.6;
            margin: 0;
            padding: 20px;
        }

        h1 {
            margin-bottom: 20px;
        }

        h2 {
            margin-top: 20px;
        }

        pre {
            background-color: #f4f4f4;
            padding: 10px;
            border-radius: 5px;
            overflow-x: auto;
        }

        code {
            font-family: Consolas, monospace;
            color: #c7254e;
            background-color: #f9f2f4;
            padding: 2px 4px;
            border-radius: 4px;
        }

        table {
            border-collapse: collapse;
            width: 100%;
            margin-bottom: 20px;
        }

        th, td {
            border: 1px solid #dddddd;
            text-align: left;
            padding: 8px;
        }

        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>

<h1>Timezone POC </h1>

<section>
    <h2>Overview</h2>
    <p>This document outlines a Proof of Concept (POC) for converting request times from the client's timezone to UTC and response times from UTC back to the client's timezone.</p>
</section>

<section>
    <h2>POST Request</h2>

    <article>
        <h3>Endpoint</h3>
        <p>http://localhost:8080/api/test-timezone/</p>
    </article>

    <article>
        <h3>Headers</h3>
        <pre><code>User-TimeZone: Asia/Kabul
Content-Type: application/json</code></pre>
</article>

    <article>
        <h3>Body</h3>
        <pre><code>{
"name": "John Doe",
"email": "john@example.com",
"notificationDate": "2024-02-13T11:57:32.720477",
"localDate": "2024-02-13",
"date": "2024-02-13 13:09:34"
}</code></pre>
</article>

    <article>
        <h3>cURL</h3>
        <pre><code>curl --location 'http://localhost:8080/api/test-timezone/' \
--header 'User-TimeZone: Asia/Kabul' \
--header 'Content-Type: application/json' \
--data-raw '{
"name": "John Doe",
"email": "john@example.com",
"notificationDate": "2024-02-13T11:57:32.720477",
"localDate": "2024-02-13",
"date": "2024-02-13 13:09:34"
}'</code></pre>
</article>
</section>

<section>
    <h2>POST Response</h2>

    <article>
        <pre><code>{
    "id": 1,
    "name": "John Doe",
    "email": "john@example.com",
    "notificationDate": "2024-02-13T11:57:32.720477",
    "localDate": "2024-02-13T04:30:00.000000",
    "date": null,
    "createdAt": "2024-02-13T12:21:25.343401",
    "updatedAt": "2024-02-13T12:21:25.343401"
}</code></pre>
<p>The response returns details of the user's test timezone request including their ID, name, email, notification date, local date, and timestamps for when the data was created and last updated.</p>
</article>
</section>

<section>
    <h2>GET Request</h2>

    <article>
        <h3>Endpoint</h3>
        <p>http://localhost:8080/api/test-timezone/?User-TimeZone=Asia%2FKabul</p>
    </article>

    <article>
        <h3>cURL</h3>
        <pre><code>curl --location 'http://localhost:8080/api/test-timezone/?User-TimeZone=Asia%2FKabul'</code></pre>
    </article>
</section>

<section>
    <h2>GET Response</h2>

    <article>
        <pre><code>[
    {
        "id": 1,
        "name": "John Doe",
        "email": "john@example.com",
        "notificationDate": "2024-02-13T07:27:32.720477",
        "localDate": "2024-02-13T00:00:00.000000",
        "date": null,
        "createdAt": "2024-02-13T07:51:25.343401",
        "updatedAt": "2024-02-13T07:51:25.343401"
    }
]</code></pre>
<p>The response returns details of the user's test timezone request including their ID, name, email, notification date, local date, and timestamps for when the data was created and last updated.</p>
</article>
</section>

</body>
</html>