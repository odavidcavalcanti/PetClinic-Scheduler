type cardProps = { title: String; description: String };

export default function Card({ title, description }: cardProps) {
    return (
        <div className="bg-white shadow-md p-4 rounded-lg">
            <h2 className="font-bold">
                {title}
            </h2>
            <p>
                {description}
            </p>
        </div>
    )
}