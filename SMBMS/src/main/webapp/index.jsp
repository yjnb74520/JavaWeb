<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<%! int price=1234;
    String discount="八折";

%>
<table border="1" style="width: 500px;text-align: center;"  >
    <tr style="background-color: #0c89de;color: white;" >
        <th>图片</th>
        <th>价格</th>
        <th>折扣</th>
        <th>折后价</th>
        <th style="width: 300px"> 文字说明</th>


    </tr>
    <tr style="border-spacing: 5px;background-color: #4f8ace">
        <td style="background-color: #0c89de;">1.jpg</td>
        <td><%=price%></td>
        <td>$<%=discount%></td>
        <td >$<%=price*0.8%></td>
        <td style="width: 100px">xxxxxxxxxxxx</td>
    </tr>
</table>
</body>
</html>
