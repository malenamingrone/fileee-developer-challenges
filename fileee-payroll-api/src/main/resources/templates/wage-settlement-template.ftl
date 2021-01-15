<!DOCTYPE html>
<html lang="en">
<head>
    <title>Wage Settlement</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
    <link rel="stylesheet" href="./wage-settlement-template_files/style.css" media="all"></link>
    <style type="text/css">
        html, body {
            width: 100%;
            margin-left: auto !important;
            margin-right: auto !important;
        }

        .clearfix:after {
            content: "";
            display: table;
            clear: both;
        }

        a {
            color: #5D6975;
            text-decoration: underline;
        }

        body {
            position: relative;
            margin: 0 auto;
            color: #001028;
            background: #FFFFFF;
            font-family: Arial, sans-serif;
            font-size: 12px;
            font-family: Arial;
        }

        header {
            padding: 10px 0;
            margin-bottom: 30px;
        }

        #logo {
            text-align: right;
            margin-bottom: 10px;
        }

        #logo img {
            width: 150px;
        }

        h1 {
            border-top: 1px solid  #5D6975;
            border-bottom: 1px solid  #5D6975;
            color: #5D6975;
            font-size: 2.4em;
            line-height: 1.4em;
            font-weight: normal;
            text-align: center;
            margin: 0 0 20px 0;
        }

        #project {
            float: left;
            font-size: 1.2em;
        }

        #project span {
            color: #5D6975;
            text-align: right;
            margin-right: 10px;
            display: inline-block;
            font-size: 0.9em;
            margin-top: 0.4em;
        }

        #project div {
            white-space: nowrap;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            border-spacing: 0;
            margin-bottom: 20px;
        }

        table tr:nth-child(2n-1) td {
            background: #F5F5F5;
        }

        table th,
        table td {
            text-align: left;
        }

        table th {
            padding: 5px 20px;
            color: #5D6975;
            border-bottom: 1px solid #C1CED9;
            white-space: nowrap;
            font-weight: normal;
        }

        table .total {
            text-align: right;
        }

        table td {
            padding: 20px;
            text-align: left;
        }

        table td.period,
        table td.salary-type,
        table td.total {
            font-size: 1.2em;
        }
    </style>
</head>
<body>
<header class="clearfix">
    <div id="logo">
        <img alt="logo" src="data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIGhlaWdodD0iNDgiIHdpZHRoPSIxNTUiPjxkZWZzPjxzdHlsZT4uY2xzLTF7ZmlsbDojODBjMjhmfTwvc3R5bGU+PC9kZWZzPjxwYXRoIGNsYXNzPSJjbHMtMSIgZD0iTTAgMHY0OGg0OFYwem0yOS42NiA2LjMybDMuNjYgMi41Ni0xMC41MyAxLjg2LS44Mi0zLjA2em0tNS4yMiAzNS4zN2wtMTAuMTEtMTQuMyAzLjU1LS40NyA5LjU2IDEzLjUxem0tMi42OS0xNS4yN2wzLjE0LS40MSAyLjQ0IDEzLjgzLTQuMzctNi41OHptLTcuNjEuNzFsMS40My0zLjQ2IDUuNTItMS0yLTExLjMgMi41Ni0zLjY2IDIuNTggMTQuNDIgNS41Mi0xIC41NSAzLjExem0xNi42MS0xNC41OGwtLjQ1LTIuNTMgMy4wNy0uODIuNDkgMi44eiIvPjxnPjxwYXRoIGNsYXNzPSJjbHMtMSIgZD0iTTEwNjIuODMgNDcxLjU2Yy01LjA2IDAtOC4zMy0zLjc3LTguMzMtOS42MXYtLjQ3YzAtNS42NSAzLjI3LTkuNDUgOC4xNC05LjQ1YTcuNTMgNy41MyAwIDAgMSA1Ljc3IDIuNDggMTAuNTIgMTAuNTIgMCAwIDEgMi4zNyA3LjE4di42OWgtMTMuNDN2LjM4YzAgNC4wNyAyLjc1IDUuODkgNS40OCA1Ljg5YTcgNyAwIDAgMCA0LjM5LTEuNTNsMS41MyAyLjIyYTkgOSAwIDAgMS01LjkyIDIuMjJ6bS0uMTktMTYuOGMtMi45MyAwLTQuNTkgMS41LTUuMDggNC41N3YuM2gxMC4yOHYtLjNjLS40OS0zLjA3LTIuMTUtNC41Ni01LjA4LTQuNTd6IiB0cmFuc2Zvcm09InRyYW5zbGF0ZSgtOTE1Ljc3IC00MzIuNCkiLz48cGF0aCBkYXRhLW5hbWU9ImUiIGNsYXNzPSJjbHMtMSIgZD0iTTEwNDMuMzYgNDcxLjY5Yy01LjE0IDAtOC40Ni0zLjgyLTguNDYtOS43NHYtLjQ3YzAtNS43MyAzLjMyLTkuNTggOC4yNy05LjU4YTcuNjYgNy42NiAwIDAgMSA1Ljg3IDIuNTIgMTAuNjUgMTAuNjUgMCAwIDEgMi40IDcuMjZ2LjgySDEwMzh2LjI1YzAgNCAyLjY5IDUuNzYgNS4zNiA1Ljc2YTYuOTMgNi45MyAwIDAgMCA0LjQyLTEuNTlsMS42NyAyLjQyYTkuMTEgOS4xMSAwIDAgMS02LjA5IDIuMzV6bS0uMTktMTYuOGMtMi45IDAtNC40NyAxLjQzLTUgNC40NnYuMTVoMTB2LS4xNWMtLjQ4LTMtMi4wNi00LjQ1LTUtNC40NnoiIHRyYW5zZm9ybT0idHJhbnNsYXRlKC05MTUuNzcgLTQzMi40KSIvPjxwYXRoIGRhdGEtbmFtZT0iZSIgY2xhc3M9ImNscy0xIiBkPSJNMTAzMS45MSA0NjIuNjN2LS45NWMwLTYuMzgtMy42OS05LjkxLTguMzktOS45MS01IDAtOC4zOSAzLjkzLTguMzkgOS43MXYuNTJjMCA2LjA5IDMuNSA5Ljg3IDguNTkgOS44N2E5LjEzIDkuMTMgMCAwIDAgNi4yNi0yLjQ3bC0xLjgxLTIuNjNhNi43OSA2Ljc5IDAgMCAxLTQuNDUgMS42NWMtMi43OCAwLTUuMjMtMS45My01LjIzLTUuNjN2LS4xMnptLTguMzktNy42YzMuMjUgMCA0LjQ0IDEuOTEgNC44MyA0LjM1aC05LjY4Yy40MS0yLjQ1IDEuNi00LjM4IDQuODUtNC4zOHoiIHRyYW5zZm9ybT0idHJhbnNsYXRlKC05MTUuNzcgLTQzMi40KSIvPjxwYXRoIGNsYXNzPSJjbHMtMSIgZD0iTTEwMTEuMzYgNDY3LjY2YTIuMDYgMi4wNiAwIDAgMS0xLjU5LjU5IDEuODYgMS44NiAwIDAgMS0yLTEuNjR2LTI1LjI4aC0zLjQ3djI1LjA5YTUuNSA1LjUgMCAwIDAgNS40OSA1LjQ5IDUuNDQgNS40NCAwIDAgMCAzLjYzLTEuMzd6IiB0cmFuc2Zvcm09InRyYW5zbGF0ZSgtOTE1Ljc3IC00MzIuNCkiLz48Zz48cGF0aCBjbGFzcz0iY2xzLTEiIGQ9Ik03OS42MyAxOS4zNWgzLjQ3djE5LjcyaC0zLjQ3ek03OS42MyAxMC4xNmgzLjQ3djQuOTFoLTMuNDd6Ii8+PC9nPjxwYXRoIGRhdGEtbmFtZT0iZiIgY2xhc3M9ImNscy0xIiBkPSJNOTg1LjcgNDQwLjg4YTcuMDggNy4wOCAwIDAgMC03LjA3IDd2MjMuNjFoMy40N3YtMTYuMjhoNi45di0zLjQ3aC02Ljl2LTMuNzlhMy42IDMuNiAwIDAgMSA3LjEzLS43bDMuNDItLjZhNy4wNyA3LjA3IDAgMCAwLTYuOTUtNS43N3oiIHRyYW5zZm9ybT0idHJhbnNsYXRlKC05MTUuNzcgLTQzMi40KSIvPjwvZz48L3N2Zz4=" />
    </div>
    <div id="project">
        <div>${employeeName}</div>
        <div><span>WAGE SETTLEMENT</span></div>
    </div>
</header>
<main>
    <table>
        <thead>
        <tr>
            <th>PERIOD</th>
            <th>SALARY TYPE</th>
            <th class="total">TOTAL</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td class="period">${period}</td>
            <td class="salary-type">${salaryType}</td>
            <td class="total">${totalAmount}</td>
        </tr>
        </tbody>
    </table>
</main>
</body>
</html>